import { Component, OnInit } from '@angular/core';
import { PlannerService } from '../../services/planner.service';
import { ActivatedRoute, Router } from '@angular/router';
import { FlashMessageService } from '../../services/flash-message.service';
import { Itinerary } from '../../models/itinerary';

@Component({
  selector: 'app-planner',
  templateUrl: './planner.component.html',
  styleUrls: ['./planner.component.sass']

})
export class PlannerComponent implements OnInit {

  loading = true;
  itinerary: Itinerary;
  firstRoute = true;

  constructor(
    private plannerService: PlannerService,
    private route: ActivatedRoute,
    private router: Router,
    private fmService: FlashMessageService
  ) { }

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      this.plannerService.loadItinerary(params.id).subscribe((itinerary) => {
        if (itinerary != null) {
          this.itinerary = itinerary;
          this.loading = false;
          if (this.firstRoute){
            this.firstRoute = false;
            this.router.navigate(['/planner/' + params.id + '/' + this.itinerary.status]);
          }
        }
      }, () => {
        this.fmService.sendMessage('Cannot load itinerary at the moment. Please try again later.', 'danger');
        this.router.navigate(['/']);
      });
    });
  }

}
