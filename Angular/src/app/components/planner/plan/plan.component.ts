import { Component, OnInit } from '@angular/core';
import { Itinerary, PastTravel } from '../../../models/itinerary';
import { PlannerService } from '../../../services/planner.service';

@Component({
  selector: 'app-plan',
  templateUrl: './plan.component.html',
  styleUrls: ['./plan.component.sass']
})
export class PlanComponent implements OnInit {

  itinerary: Itinerary;

  constructor(
    private plannerService: PlannerService
  ) { }

  ngOnInit(): void {
    this.plannerService.getItinerary().subscribe(x => {
      this.itinerary = x;
      this.itinerary.plan = this.itinerary.plan.map(y => {
        y.date = new Date(y.date);
        y.events = y.events.map(z => {
          z.from_date = new Date(z.from_date);
          z.to_date = new Date(z.to_date);
          return z;
        });
        return y;
      });
    });
  }

}
