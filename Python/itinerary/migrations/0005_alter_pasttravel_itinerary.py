# Generated by Django 3.2 on 2021-04-16 07:09

from django.db import migrations, models
import django.db.models.deletion


class Migration(migrations.Migration):

    dependencies = [
        ('itinerary', '0004_auto_20210416_0706'),
    ]

    operations = [
        migrations.AlterField(
            model_name='pasttravel',
            name='itinerary',
            field=models.ForeignKey(on_delete=django.db.models.deletion.CASCADE, related_name='past_travels', to='itinerary.itinerary'),
        ),
    ]
