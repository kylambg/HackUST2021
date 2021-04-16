from rest_framework_simplejwt.serializers import TokenObtainPairSerializer
from rest_framework import serializers
from .models import UserProfile, UserStatus
import django.contrib.auth.password_validation as validators
from django.core import exceptions
from django.contrib.auth.models import User
from itinerary.models import Itinerary


class MyTokenObtainPairSerializer(TokenObtainPairSerializer):
    @classmethod
    def get_token(cls, user):
        token = super().get_token(user)
        token['user'] = {
            'first_name': user.first_name,
            'last_name': user.last_name,
            'email': user.email,
        }
        return token


class ChangePasswordSerializer(serializers.Serializer):

    def create(self, validated_data):
        pass

    def update(self, instance, validated_data):
        if not instance.check_password(validated_data['old_password']):
            raise serializers.ValidationError('Old password does not match.')
        try:
            validators.validate_password(password=validated_data['new_password'], user=User)
        except exceptions.ValidationError as e:
            raise serializers.ValidationError('ea'.join(e))
        instance.set_password(validated_data['new_password'])
        instance.save()
        return instance

    model = User
    old_password = serializers.CharField(required=True, write_only=True)
    new_password = serializers.CharField(required=True, write_only=True)


class UserSerializer(serializers.ModelSerializer):

    password = serializers.CharField(write_only=True)

    def create(self, validated_data):
        # TODO validation
        user = User.objects.create_user(**validated_data)
        UserProfile.objects.create(user=user)
        return user

    class Meta:
        model = User
        fields = ("username", "password", "first_name", "last_name", "email")


class UserStatusSerializer(serializers.ModelSerializer):
    country = serializers.SerializerMethodField()

    class Meta:
        model = UserStatus
        fields = ("id", "name", "country")

    @staticmethod
    def get_country(obj):
        return dict(name=obj.country.name, iso_code=obj.country.iso_code)


class UserItinerarySerializer(serializers.ModelSerializer):
    name = serializers.SerializerMethodField()

    class Meta:
        model = Itinerary
        fields = ("id", "name", "created_at")

    @staticmethod
    def get_name(obj):
        # Todo
        return "Hello"
