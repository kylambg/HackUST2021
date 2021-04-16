import { Component, OnInit } from '@angular/core';
import { UserService } from '../../../services/user.service';
import { FlashMessageService } from '../../../services/flash-message.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-itinerary-list',
  templateUrl: './itinerary-list.component.html',
  styles: [
  ]
})
export class ItineraryListComponent implements OnInit {

  saving = false;
  loading = true;
  itineraries: Array<any> = [];

  constructor(
    private userService: UserService,
    private fmService: FlashMessageService,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.userService.getItineraries().subscribe(itineraries => {
      this.itineraries = itineraries;
      this.loading = false;
    });
  }

  newItinerary(): void {
    this.saving = true;
    this.userService.addItinerary().subscribe((response) => {
      this.router.navigate(['/planner/' + response.id]);
    }, () => {
      this.fmService.sendMessage('Cannot create itinerary. Try again later.', 'danger');
      this.saving = false;
    });
  }

  removeItinerary(id): void {
    if (this.saving) {
      return;
    }
    this.saving = true;
    this.userService.removeItinerary(id).subscribe((itineraries) => {
      this.itineraries = itineraries;
      this.saving = false;
    }, () => {
      this.fmService.sendMessage('Cannot remove itinerary. Try again later.', 'danger');
      this.saving = false;
    });
  }



}
