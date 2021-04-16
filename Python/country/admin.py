from django.contrib import admin
from .models import Country, Region, City, Quarantine, RestrictedEntry, CovidTesting


@admin.register(Country)
class CountryAdmin(admin.ModelAdmin):
    list_display = ('name',)


@admin.register(Region)
class RegionAdmin(admin.ModelAdmin):
    list_display = ('name', 'safety_level', 'country')


@admin.register(City)
class CityAdmin(admin.ModelAdmin):
    list_display = ('name', 'region')


@admin.register(Quarantine)
class QuarantineAdmin(admin.ModelAdmin):
    list_display = ('region',)


@admin.register(RestrictedEntry)
class RestrictedEntryAdmin(admin.ModelAdmin):
    list_display = ('region',)


@admin.register(CovidTesting)
class CovidTestingAdmin(admin.ModelAdmin):
    list_display = ('region',)
