import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppComponent } from './app.component';
import {
  NgbDateAdapter,
  NgbDateNativeAdapter,
  NgbDateParserFormatter,
  NgbDatepickerModule,
  NgbTypeaheadModule
} from '@ng-bootstrap/ng-bootstrap';
import { MainComponent } from './components/main/main.component';
import { LoginComponent } from './components/login/login.component';
import { RouterModule } from '@angular/router';
import { WelcomeComponent } from './components/welcome/welcome.component';
import { PlannerComponent } from './components/planner/planner.component';
import { HttpClientModule } from '@angular/common/http';
import { AuthGuard } from './services/auth.guard';
import { FormsModule } from '@angular/forms';
import { StatusComponent } from './components/welcome/status/status.component';
import { ItineraryListComponent } from './components/welcome/itinerary-list/itinerary-list.component';
import { LoadingSpinnerComponent } from './components/loading-spinner/loading-spinner.component';
import { OriginComponent } from './components/planner/origin/origin.component';
import { DestinationComponent } from './components/planner/destination/destination.component';
import { PlanComponent } from './components/planner/plan/plan.component';
import { CityTypeaheadComponent } from './components/planner/city-typeahead/city-typeahead.component';
import { NgbDateFormatter } from './services/ngb-date-formatter';

const routes = [
  { path: 'login', component: LoginComponent, data: {restricted: false}, canActivate: [AuthGuard]  },
  { path: '', component: MainComponent, data: {restricted: true}, canActivate: [AuthGuard], children: [
      {path: 'planner/:id', component: PlannerComponent, children: [
          {path: 'origin', component: OriginComponent, data: {stage: 'origin'}},
          {path: 'destination', component: DestinationComponent, data: {stage: 'origin'}},
          {path: 'plan', component: PlanComponent, data: {stage: 'origin'}},
          {path: '', component: OriginComponent, data: {stage: 'origin'}}
        ]},
      {path: '', component: WelcomeComponent}
    ] },
];

@NgModule({
  declarations: [
    AppComponent,
    MainComponent,
    LoginComponent,
    WelcomeComponent,
    PlannerComponent,
    StatusComponent,
    ItineraryListComponent,
    LoadingSpinnerComponent,
    OriginComponent,
    DestinationComponent,
    PlanComponent,
    CityTypeaheadComponent
  ],
  imports: [
    HttpClientModule,
    BrowserModule,
    RouterModule.forRoot(routes),
    FormsModule,
    NgbTypeaheadModule,
    NgbDatepickerModule
  ],
  providers: [
    {provide: NgbDateAdapter, useClass: NgbDateNativeAdapter},
    {provide: NgbDateParserFormatter, useClass: NgbDateFormatter}
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
