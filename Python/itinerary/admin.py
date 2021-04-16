from django.contrib import admin
from .models import Itinerary, PastTravel


class PastTravelsInline(admin.TabularInline):
    model = PastTravel
    extra = 0


@admin.register(Itinerary)
class ItineraryAdmin(admin.ModelAdmin):
    list_display = ['user', 'created_at']
    inlines = [PastTravelsInline]
