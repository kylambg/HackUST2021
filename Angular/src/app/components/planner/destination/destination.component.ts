import { Component, OnInit } from '@angular/core';
import { Destination, Itinerary } from '../../../models/itinerary';
import { PlannerService } from '../../../services/planner.service';
import { FlashMessageService } from '../../../services/flash-message.service';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-destination',
  templateUrl: './destination.component.html',
  styles: [
  ]
})
export class DestinationComponent implements OnInit {

  itinerary: Itinerary;
  destinations: Array<Destination> = [
    { city: undefined, date: new Date() }
  ];

  saving = false;

  constructor(
    private plannerService: PlannerService,
    private fmService: FlashMessageService,
    private router: Router,
    private route: ActivatedRoute
  ) { }

  ngOnInit(): void {
    this.plannerService.getItinerary().subscribe(x => {
      if (x != null){
        console.log(x);
        this.itinerary = x;
        this.itinerary.origin_details.past_travels = this.itinerary.origin_details.past_travels.filter(d => d.city != null);
        if (this.itinerary.status === 'plan') {
          this.destinations = this.itinerary.destinations.map(y => {
            y.date = new Date(y.date);
            return y;
          });
        }
      }
    });
  }

  addDestination(): void {
    this.destinations.push({ city: undefined, date: new Date()});
  }

  save(): void {
    this.saving = true;
    let destinations = {};
    this.destinations.forEach((destination) => {
      if (destination.city == null || destination.date == null){
        return;
      }
      if (!(destination.city.id in destinations)) {
        destinations[destination.city.id] = destination.date;
      }
    });
    destinations = Object.entries(destinations).map(x => ({
      id: x[0], date: (x[1] as Date).toLocaleDateString('fr-CA')
    }));
    if ((destinations as any[]).length === 0){
      this.saving = false;
      return this.fmService.sendMessage('Select at least one destination with date.', 'danger');
    }
    this.plannerService.saveDestinations({destinations}).subscribe(() => {
      this.router.navigate(['../plan'], {relativeTo: this.route});
    }, (e) => {
      if (e.status === 400) {
        this.fmService.sendMessage(e.error.error, 'danger', 'Invalid Information');
      } else {
        this.fmService.sendMessage('Cannot record data. Please try again later', 'danger');
      }
      this.saving = false;
    });
  }

  removeDestination(i): void {
    this.destinations.splice(i, 1);
  }
}
