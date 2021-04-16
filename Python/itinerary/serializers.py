from rest_framework import serializers

from itinerary.models import Itinerary, PastTravel, Destination
from country.views import CitySerializer


class PastTravelSerializer(serializers.ModelSerializer):
    city = CitySerializer()

    class Meta:
        model = PastTravel
        fields = ('city', 'last_date')


class DestinationSerializer(serializers.ModelSerializer):
    city = CitySerializer()

    class Meta:
        model = Destination
        fields = ('city', 'date')


class ItinerarySerializer(serializers.ModelSerializer):
    origin_details = serializers.SerializerMethodField()
    destinations = serializers.SerializerMethodField()

    class Meta:
        model = Itinerary
        fields = ('created_at', 'status', 'origin_details', 'destinations', 'plan')

    @staticmethod
    def get_origin_details(obj):
        if obj.status == 'origin':
            return None
        return dict(
            leaving_from=CitySerializer(obj.leaving_from).data,
            departure_date=obj.departure_date,
            past_travels=PastTravelSerializer(obj.past_travels.all(), many=True).data
        )

    @staticmethod
    def get_destinations(obj):
        if obj.status != 'plan':
            return None
        return DestinationSerializer(obj.destinations, many=True).data
