from rest_framework import filters
from rest_framework import serializers
from rest_framework.generics import ListAPIView
from rest_framework.permissions import IsAuthenticated
from .models import City, Region, Country


class CountrySerializer(serializers.ModelSerializer):
    class Meta:
        model = Country
        fields = ('id', 'name', 'iso_code')


class RegionSerializer(serializers.ModelSerializer):
    country = CountrySerializer()

    class Meta:
        model = Region
        fields = ('id', 'name', 'safety_level', 'country')


class CitySerializer(serializers.ModelSerializer):
    region = RegionSerializer()

    class Meta:
        model = City
        fields = ('id', 'name', 'airport_code', 'region')


class CityListView(ListAPIView):
    # Todo
    # permission_classes = [IsAuthenticated]
    queryset = City.objects.all()
    filter_backends = [filters.OrderingFilter, filters.SearchFilter]
    ordering = ['name']
    search_fields = ['name', 'airport_code', 'region__name', 'region__country__name']
    serializer_class = CitySerializer
