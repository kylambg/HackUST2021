import { Component, OnInit } from '@angular/core';
import { Observable, of } from 'rxjs';
import { catchError, debounceTime, distinctUntilChanged, switchMap, tap } from 'rxjs/operators';
import { OriginDetails, PastTravel } from '../../../models/itinerary';
import { FlashMessageService } from '../../../services/flash-message.service';
import { PlannerService } from '../../../services/planner.service';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-origin',
  templateUrl: './origin.component.html',
  styles: [
  ]
})
export class OriginComponent implements OnInit {

  originDetails: OriginDetails = {
    leaving_from: undefined,
    departure_date: new Date(),
    past_travels: [
      {
        city: undefined,
        last_date: new Date()
      }
    ]
  };

  saving = false;

  constructor(
    private fmService: FlashMessageService,
    private plannerService: PlannerService,
    private router: Router,
    private route: ActivatedRoute
  ) { }

  ngOnInit(): void {
    this.plannerService.getItinerary().subscribe(x => {
      if (x != null && x.status !== 'origin') {
        this.originDetails = x.origin_details;
        this.originDetails.departure_date = new Date(this.originDetails.departure_date);
        this.originDetails.past_travels.map((y: PastTravel) => {
          y.last_date = new Date(y.last_date);
          return y;
        });
        if (this.originDetails.past_travels.length === 0) {
          this.addPastTravel();
        }
      }
    });
  }

  addPastTravel(): void {
    this.originDetails.past_travels.push({
      city: undefined,
      last_date: new Date()
    });
  }

  save(): void{
    this.saving = true;
    if (this.originDetails.leaving_from === null || this.originDetails.departure_date < new Date()) {
      return this.fmService.sendMessage('Choose a departure city and date in the future.', 'danger');
    }
    const pastTravels = {};
    this.originDetails.past_travels.forEach((destination) => {
      if (destination.city == null || destination.city.id === this.originDetails.leaving_from.id
        || destination.last_date == null){
        return;
      }
      if (!(destination.city.id in pastTravels)) {
        pastTravels[destination.city.id] = destination.last_date;
      } else {
        if (destination.last_date > pastTravels[destination.city.id]) {
          pastTravels[destination.city.id] = destination.last_date;
        }
      }
    });
    const data = {
      leaving_from: this.originDetails.leaving_from.id,
      departure_date: this.originDetails.departure_date,
      past_travels: Object.entries(pastTravels).map(x => ({
        id: x[0], last_date: x[1]
      }))
    };
    this.plannerService.saveOriginDetails(data).subscribe(() => {
      this.router.navigate(['../destination'], {relativeTo: this.route});
    }, () => {
      this.fmService.sendMessage('Cannot record data. Please try again later', 'danger');
      this.saving = false;
    });
  }

  removeHistory(i): void {
    this.originDetails.past_travels.splice(i, 1);
  }

}
