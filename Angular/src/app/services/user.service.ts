import { Injectable } from '@angular/core';
import { AuthService } from './auth.service';
import { EndpointsService } from './endpoints.service';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(
    private authService: AuthService,
    private endpoints: EndpointsService
  ) { }

  getUserStatusList = (keyword: string) => this.authService.getRequest(this.endpoints.user.statusList, {search: keyword});
  /* tslint:disable-next-line */
  getUserStatusSearchFn = this.getUserStatusList.bind(this);

  getUserStatus = () => this.authService.getRequest(this.endpoints.user.status);
  addUserStatus = (id) => this.authService.postRequest(this.endpoints.user.status, undefined, {id});
  removeUserStatus = (id) => this.authService.deleteRequest(this.endpoints.user.status, {id});

  getItineraries = () => this.authService.getRequest(this.endpoints.user.itinerary);
  addItinerary = () => this.authService.postRequest(this.endpoints.user.itinerary);
  removeItinerary = (id) => this.authService.deleteRequest(this.endpoints.user.itinerary, {id});
}
