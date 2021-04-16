from rest_framework_simplejwt.views import TokenObtainPairView
from rest_framework.decorators import api_view, permission_classes
from rest_framework.serializers import ValidationError
from rest_framework.response import Response
from django.contrib.auth.models import User
from .models import UserStatus
from rest_framework.generics import CreateAPIView, ListAPIView
from rest_framework import permissions
from .serializers import UserSerializer, MyTokenObtainPairSerializer, ChangePasswordSerializer, UserStatusSerializer, \
    UserItinerarySerializer
from rest_framework.permissions import IsAuthenticated
from rest_framework import filters, status
from itinerary.models import Itinerary


class MyTokenObtainPairView(TokenObtainPairView):
    serializer_class = MyTokenObtainPairSerializer


@api_view(['PUT'])
def change_password(request, *args, **kwargs):
    user = request.user
    serializer = ChangePasswordSerializer(user, data=request.data)
    if serializer.is_valid():
        try:
            serializer.save()
        except ValidationError as e:
            return Response(e.detail, status=status.HTTP_400_BAD_REQUEST)
        return Response(serializer.data)
    return Response('Cannot update password. Ensure that all fields are valid.', status=status.HTTP_400_BAD_REQUEST)


class CreateUserView(CreateAPIView):
    model = User
    permission_classes = [permissions.AllowAny]
    serializer_class = UserSerializer


class UserStatusListView(ListAPIView):
    permission_classes = [IsAuthenticated]
    queryset = UserStatus.objects.all()
    filter_backends = [filters.OrderingFilter, filters.SearchFilter]
    ordering = ['name']
    search_fields = ['name', 'country__name']
    serializer_class = UserStatusSerializer


@api_view(['GET', 'POST', 'DELETE'])
@permission_classes([IsAuthenticated])
def manage_user_status(request):
    if request.method == 'GET':
        return Response(UserStatusSerializer(request.user.profile.status.all(), many=True).data)
    if request.method == 'POST':
        try:
            status_id = request.data['id']
            status_obj = UserStatus.objects.get(id=status_id)
        except KeyError:
            return Response(status=status.HTTP_400_BAD_REQUEST)
        except UserStatus.DoesNotExist:
            return Response(status=status.HTTP_400_BAD_REQUEST)
        request.user.profile.status.add(status_obj)
        return Response(UserStatusSerializer(request.user.profile.status.all(), many=True).data)
    if request.method == 'DELETE':
        try:
            status_id = request.query_params['id']
            status_obj = UserStatus.objects.get(id=status_id)
        except KeyError:
            return Response(status=status.HTTP_400_BAD_REQUEST)
        except UserStatus.DoesNotExist:
            return Response(status=status.HTTP_400_BAD_REQUEST)
        request.user.profile.status.remove(status_obj)
        return Response(UserStatusSerializer(request.user.profile.status.all(), many=True).data)


@api_view(['GET', 'POST', 'DELETE'])
@permission_classes([IsAuthenticated])
def manage_user_itineraries(request):
    if request.method == 'GET':
        return Response(UserItinerarySerializer(request.user.itineraries.all(), many=True).data)
    if request.method == 'POST':
        itinerary = Itinerary.objects.create(user=request.user)
        return Response({'id': itinerary.id})
    if request.method == 'DELETE':
        try:
            it_id = request.query_params['id']
            itinerary = Itinerary.objects.get(id=it_id)
        except KeyError:
            return Response(status=status.HTTP_400_BAD_REQUEST)
        except UserStatus.DoesNotExist:
            return Response(status=status.HTTP_400_BAD_REQUEST)
        itinerary.delete()
        return Response(UserItinerarySerializer(request.user.itineraries.all(), many=True).data)
