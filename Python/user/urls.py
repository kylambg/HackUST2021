from django.urls import path
from rest_framework_simplejwt.views import TokenRefreshView
from .views import change_password, MyTokenObtainPairView, CreateUserView, UserStatusListView, \
    manage_user_status, manage_user_itineraries

urlpatterns = [
    path('register/', CreateUserView.as_view()),
    path('token/', MyTokenObtainPairView.as_view()),
    path('token/refresh/', TokenRefreshView.as_view(), name='token_refresh'),
    path('change-password/', change_password),

    path('user/status-list/', UserStatusListView.as_view()),
    path('user/status/', manage_user_status),
    path('user/itinerary/', manage_user_itineraries),
]
