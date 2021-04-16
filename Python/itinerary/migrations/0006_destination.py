# Generated by Django 3.2 on 2021-04-16 10:38

from django.db import migrations, models
import django.db.models.deletion


class Migration(migrations.Migration):

    dependencies = [
        ('country', '0004_city'),
        ('itinerary', '0005_alter_pasttravel_itinerary'),
    ]

    operations = [
        migrations.CreateModel(
            name='Destination',
            fields=[
                ('id', models.BigAutoField(auto_created=True, primary_key=True, serialize=False, verbose_name='ID')),
                ('date', models.DateField()),
                ('city', models.ForeignKey(on_delete=django.db.models.deletion.PROTECT, to='country.city')),
                ('itinerary', models.ForeignKey(on_delete=django.db.models.deletion.CASCADE, related_name='destinations', to='itinerary.itinerary')),
            ],
        ),
    ]
