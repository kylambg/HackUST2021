from django.urls import path
from .views import get_itinerary, save_origin_details, generate_plan


urlpatterns = [
    path('', get_itinerary),
    path('origin/', save_origin_details),
    path('destinations/', generate_plan),

]
