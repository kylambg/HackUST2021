import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class EndpointsService {

  constructor() { }

  baseUrl: string = environment.baseUrl;
  getToken: string =  this.baseUrl + 'token/';
  register: string =  this.baseUrl + 'register/';
  refreshToken: string =  this.baseUrl + 'token/refresh/';
  changePassword: string = this.baseUrl + 'change-password/';

  user = {
    statusList: this.baseUrl + 'user/status-list/',
    status: this.baseUrl + 'user/status/',
    itinerary: this.baseUrl + 'user/itinerary/',
  };

  itinerary = {
    get: (id) => this.baseUrl + `itinerary/${id}/`,
    originDetails: (id) => this.baseUrl + `itinerary/${id}/origin/`,
    destinationDetails: (id) => this.baseUrl + `itinerary/${id}/destinations/`,
    cities: this.baseUrl + 'cities',
  };

}
