from django.contrib.auth.models import User
from django.db import models

from country.models import Country


class UserStatus(models.Model):
    name = models.CharField(max_length=500)
    country = models.ForeignKey(Country, on_delete=models.CASCADE)

    def __str__(self):
        return self.name


class UserProfile(models.Model):
    user = models.OneToOneField(User, on_delete=models.CASCADE, related_name='profile')
    status = models.ManyToManyField(UserStatus, blank=True)
