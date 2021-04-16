from django.db import models
from django.contrib.auth.models import User
from django.utils import timezone

from country.models import City


class Itinerary(models.Model):
    ORIGIN = 'origin'
    DESTINATION = 'destination'
    PLAN = 'plan'
    STATUS_CHOICES = (
        (ORIGIN, 'Origin'),
        (DESTINATION, 'Destination'),
        (PLAN, 'Plan'),
    )
    user = models.ForeignKey(User, on_delete=models.CASCADE, related_name='itineraries')
    created_at = models.DateTimeField(default=timezone.now)
    status = models.CharField(max_length=11, choices=STATUS_CHOICES, default='origin')

    leaving_from = models.ForeignKey(City, on_delete=models.PROTECT, null=True, blank=True)
    departure_date = models.DateField(null=True, blank=True)
    plan = models.JSONField(null=True, blank=True)


class PastTravel(models.Model):
    itinerary = models.ForeignKey(Itinerary, on_delete=models.CASCADE, related_name='past_travels')
    city = models.ForeignKey(City, on_delete=models.PROTECT)
    last_date = models.DateField()


class Destination(models.Model):
    itinerary = models.ForeignKey(Itinerary, on_delete=models.CASCADE, related_name='destinations')
    city = models.ForeignKey(City, on_delete=models.PROTECT)
    date = models.DateField()

