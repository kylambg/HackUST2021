# Generated by Django 3.2 on 2021-04-16 00:51

from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ('user', '0001_initial'),
    ]

    operations = [
        migrations.AlterField(
            model_name='userprofile',
            name='status',
            field=models.ManyToManyField(blank=True, to='user.UserStatus'),
        ),
    ]