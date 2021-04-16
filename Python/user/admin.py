from django.contrib import admin
from .models import UserStatus


@admin.register(UserStatus)
class UserStatusAdmin(admin.ModelAdmin):
    list_display = ('name', 'country')

