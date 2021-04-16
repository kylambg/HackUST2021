from django.shortcuts import render
from rest_framework import status
from rest_framework.decorators import api_view, permission_classes
from rest_framework.permissions import IsAuthenticated
from rest_framework.response import Response
from datetime import datetime, date, timedelta

from country.views import CitySerializer
from .models import Itinerary, PastTravel, Destination
from country.models import City, Quarantine, CovidTesting, RestrictedEntry
from .serializers import ItinerarySerializer
from django.db.models import Q


@api_view(['GET'])
@permission_classes([IsAuthenticated])
def get_itinerary(request, pk):
    try:
        itinerary = Itinerary.objects.get(id=pk, user=request.user)
    except Itinerary.DoesNotExist:
        return Response(status=status.HTTP_400_BAD_REQUEST)

    return Response(ItinerarySerializer(itinerary).data)


@api_view(['POST'])
@permission_classes([IsAuthenticated])
def save_origin_details(request, pk):
    try:
        itinerary = Itinerary.objects.get(id=pk, user=request.user)
    except Itinerary.DoesNotExist:
        return Response(status=status.HTTP_400_BAD_REQUEST)

    itinerary.past_travels.all().delete()
    itinerary.destinations.all().delete()
    itinerary.plan = None

    try:
        leaving_from = request.data['leaving_from']
        departure_date = request.data['departure_date']
        past_travels = request.data['past_travels']
        itinerary.leaving_from = City.objects.get(pk=leaving_from)
        itinerary.departure_date = datetime.strptime(departure_date[:10], "%Y-%m-%d")
        for travel in past_travels:
            PastTravel.objects.create(
                itinerary=itinerary,
                city=City.objects.get(pk=travel['id']),
                last_date=datetime.strptime(travel['last_date'][:10], "%Y-%m-%d")
            )
    except Exception:
        return Response(status=status.HTTP_400_BAD_REQUEST)

    itinerary.status = 'destination'
    itinerary.save()

    return Response(ItinerarySerializer(itinerary).data, status=status.HTTP_200_OK)


def if_applicable(condition, status_list, travel_history, current_travel):
    groups_covered = list(condition.groups_covered.all().values_list('id', flat=True))
    groups_excluded = list(condition.groups_excluded.all().values_list('id', flat=True))
    covered = False
    if len(groups_covered) > 0:
        for user_status in status_list:
            if user_status.id in groups_covered:
                covered = True
                break
    else:
        covered = True
    for user_status in status_list:
        if user_status.id in groups_excluded:
            covered = False
            break
    if not covered:
        return False
    if condition.visit_days is not None and condition.visit_days > 0:
        covered = False
        excluded = False
        for travel in reversed(current_travel):
            if (date.today() - travel['last_date']).days > condition.visit_days:
                break
            if travel['city'].region in condition.regions_covered:
                covered = True
            if travel['city'].region in condition.regions_excluded:
                excluded = True
        for travel in reversed(list(travel_history)):
            if (date.today() - travel.last_date).days > condition.visit_days:
                break
            if travel.city.region in condition.regions_covered:
                covered = True
            if travel.city.region in condition.regions_excluded:
                excluded = True
        if excluded or (not covered):
            return False
    return True


@api_view(['POST'])
@permission_classes([IsAuthenticated])
def generate_plan(request, pk):
    try:
        itinerary = Itinerary.objects.get(id=pk, user=request.user)
    except Itinerary.DoesNotExist:
        return Response({'error': 'Unable to process request. Please try again.'}, status=status.HTTP_400_BAD_REQUEST)

    itinerary.destinations.all().delete()

    try:
        destinations = request.data['destinations']
        for destination in destinations:
            Destination.objects.create(
                itinerary=itinerary,
                city=City.objects.get(pk=destination['id']),
                date=datetime.strptime(destination['date'][:10], "%Y-%m-%d")
            )
    except Exception:
        return Response({'error': 'Unable to process request. Please try again.'}, status=status.HTTP_400_BAD_REQUEST)

    destinations = itinerary.destinations.all().order_by('date')
    plan = []

    current_travel = []
    previous_destination_max_date = None
    previous_destination = itinerary.leaving_from

    for destination in destinations:
        events = [dict(
            category='flight',
            from_location=CitySerializer(previous_destination).data,
            from_date=destination.date.strftime('%Y-%m-%d'),
            to_date=destination.date.strftime('%Y-%m-%d')
        )]

        quarantines = filter(lambda x: if_applicable(
            x, request.user.profile.status.all(), itinerary.past_travels.all(), current_travel
        ), Quarantine.objects.filter(region=destination.city.region))

        tests = filter(lambda x: if_applicable(
            x, request.user.profile.status.all(), itinerary.past_travels.all(), current_travel
        ), CovidTesting.objects.filter(region=destination.city.region))

        restricted_entry = list(filter(lambda x: if_applicable(
            x, request.user.profile.status.all(), itinerary.past_travels.all(), current_travel
        ), RestrictedEntry.objects.filter(region=destination.city.region)))

        if len(restricted_entry) > 0:
            print(restricted_entry)
            return Response({'error': 'Given your travel history and immigration status, you cannot enter ' +
                            destination.city.name + ' on ' + destination.date.strftime('%d-%b-%Y') + ' as scheduled.'}
                            , status=status.HTTP_400_BAD_REQUEST)

        max_date = destination.date
        min_date = destination.date

        for quarantine in quarantines:
            events.append(dict(
                category='quarantine',
                from_date=(destination.date+timedelta(days=quarantine.start_day)).strftime('%Y-%m-%d'),
                to_date=(destination.date+timedelta(days=quarantine.end_day)).strftime('%Y-%m-%d'),
                type=quarantine.get_type_display(),
                notes=quarantine.notes
            ))
            max_date = max(destination.date+timedelta(days=quarantine.end_day), max_date)
            min_date = min(destination.date+timedelta(days=quarantine.end_day), min_date)

        for test in tests:
            events.append(dict(
                category='test',
                from_date=(destination.date+timedelta(hours=test.start_hours)).strftime('%Y-%m-%d'),
                to_date=(destination.date+timedelta(hours=test.end_hours)).strftime('%Y-%m-%d'),
                designated_labs=test.designated_labs_only,
                notes=test.notes
            ))
            max_date = max(destination.date+timedelta(hours=test.end_hours), max_date)
            min_date = min(destination.date+timedelta(hours=test.end_hours), min_date)

        if (previous_destination_max_date is not None) and min_date < previous_destination_max_date:
            return Response({'error': 'For the given schedule there are mandatory activities in ' + previous_destination.name +
                            ' until ' + previous_destination_max_date.strftime('%d-%b-%Y') + ' but travel to ' +
                            destination.city.name + ' is on ' + destination.date.strftime('%d-%b-%Y') + '.'}
                            , status=status.HTTP_400_BAD_REQUEST)

        events = sorted(events, key=lambda x: x['from_date'])
        plan.append(dict(destination=CitySerializer(destination.city).data, events=events,
                         date=destination.date.strftime('%Y-%m-%d')))
        current_travel.append(dict(city=previous_destination, last_date=destination.date))
        previous_destination = destination.city

    itinerary.plan = plan
    itinerary.status = 'plan'
    itinerary.save()
    return Response(ItinerarySerializer(itinerary).data, status=status.HTTP_200_OK)
