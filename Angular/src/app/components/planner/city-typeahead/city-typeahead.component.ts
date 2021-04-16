import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { Observable, of } from 'rxjs';
import { catchError, debounceTime, distinctUntilChanged, tap, switchMap } from 'rxjs/operators';
import { City } from '../../../models/itinerary';
import { PlannerService } from '../../../services/planner.service';

@Component({
  selector: 'app-city-typeahead',
  templateUrl: './city-typeahead.component.html',
  styles: []
})
export class CityTypeaheadComponent implements OnInit {

  constructor(
    private plannerService: PlannerService
  ) { }

  searching = false;
  searchFailed = false;
  noItems = false;
  @Input() value: any;
  @Output() valueChange: EventEmitter<any> = new EventEmitter<any>();
  @Input() name = 'item';
  loadingText = 'Loading cities...';
  noMatchText = 'No cities match the inputted text.';
  errorText = 'Sorry, cities could not be loaded.';
  placeholderText = '';
  inputFormatter = (item: City) => item.name + ', ' + item.region.country.name;

  ngOnInit(): void {
  }

  search = (text$: Observable<string>) =>
    text$.pipe(
      debounceTime(300),
      distinctUntilChanged(),
      tap(() => this.searching = true),
      switchMap(term =>
        this.plannerService.getCities(term).pipe(
          tap((items) => {
            this.searchFailed = false;
            this.noItems = items.length <= 0;
          }),
          catchError(() => {
            this.searchFailed = true;
            return of([]);
          }))
      ),
      tap(() => this.searching = false)
    )

}
