from django.db import models
from polymorphic.models import PolymorphicModel


class Country(models.Model):
    name = models.CharField(max_length=200)
    iso_code = models.CharField(max_length=5)

    def __str__(self):
        return self.name


class Region(models.Model):
    SAFE = 'safe'
    LOW = 'low'
    MEDIUM = 'medium'
    HIGH = 'high'
    SAFETY_LEVELS = (
        (SAFE, 'Very Safe'),
        (LOW, 'Low Risk'),
        (MEDIUM, 'Medium Risk'),
        (HIGH, 'High Risk')
    )
    name = models.CharField(max_length=200)
    country = models.ForeignKey(Country, on_delete=models.CASCADE)
    safety_level = models.CharField(max_length=15, choices=SAFETY_LEVELS)

    def __str__(self):
        return self.name


class City(models.Model):
    name = models.CharField(max_length=200)
    airport_code = models.CharField(max_length=10)
    region = models.ForeignKey(Region, on_delete=models.CASCADE)

    def __str__(self):
        return self.name


class TravelCondition(PolymorphicModel):
    region = models.ForeignKey(Region, on_delete=models.CASCADE, related_name='restrictions')
    groups_covered = models.ManyToManyField('user.UserStatus', blank=True, related_name='included_travel_conditions')
    groups_excluded = models.ManyToManyField('user.UserStatus', blank=True, related_name='excluded_travel_conditions')
    visit_days = models.IntegerField(blank=True, null=True)
    regions_covered = models.ManyToManyField(Region, blank=True, related_name='origin_restrictions')
    regions_excluded = models.ManyToManyField(Region, blank=True, related_name='origin_restrictions_excluded')
    notes = models.TextField(blank=True, null=True)


class Quarantine(TravelCondition):
    DESIGNATED_HOTEL = 'designated'
    ANY_HOTEL = 'hotel_other'
    GOVERNMENT_ARRANGED = 'government'
    HOME_QUARANTINE = 'home'
    SELF_MONITORING = 'monitoring'
    QUARANTINE_TYPES = (
        (DESIGNATED_HOTEL, 'Designated Hotel'),
        (ANY_HOTEL, 'Any Hotel'),
        (GOVERNMENT_ARRANGED, 'Government Arranged'),
        (HOME_QUARANTINE, 'Home Quarantine'),
        (SELF_MONITORING, 'Self Monitoring'),
    )
    start_day = models.IntegerField()
    end_day = models.IntegerField()
    type = models.CharField(max_length=15, choices=QUARANTINE_TYPES)


class RestrictedEntry(TravelCondition):
    pass


class CovidTesting(TravelCondition):
    start_hours = models.IntegerField()
    end_hours = models.IntegerField()
    designated_labs_only = models.BooleanField()
