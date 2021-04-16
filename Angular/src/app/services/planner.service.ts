import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { AuthService } from './auth.service';
import { City, Itinerary } from '../models/itinerary';
import { EndpointsService } from './endpoints.service';
import { tap } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class PlannerService {

  id: number;
  itinerary: BehaviorSubject<Itinerary> = new BehaviorSubject<Itinerary>(null);

  constructor(
    private authService: AuthService,
    private endpoints: EndpointsService
  ) { }

  loadItinerary(id): Observable<Itinerary> {
    this.itinerary.next(null);
    this.id = id;
    this.authService.getRequest(this.endpoints.itinerary.get(id))
      .subscribe(itinerary => this.itinerary.next(itinerary), () => this.itinerary.error(null));
    return this.itinerary.asObservable();
  }

  getItinerary(): Observable<Itinerary> {
    return this.itinerary.asObservable();
  }

  getCities(keyword): Observable<City[]>{
    return this.authService.getRequest(this.endpoints.itinerary.cities, {search: keyword});
  }

  saveOriginDetails(data): Observable<any> {
    return this.authService.postRequest(this.endpoints.itinerary.originDetails(this.id), undefined, data)
      .pipe(tap(x => this.itinerary.next(x)));
  }

  saveDestinations(data): Observable<any> {
    return this.authService.postRequest(this.endpoints.itinerary.destinationDetails(this.id), undefined, data)
      .pipe(tap(x => this.itinerary.next(x)));
  }
}
