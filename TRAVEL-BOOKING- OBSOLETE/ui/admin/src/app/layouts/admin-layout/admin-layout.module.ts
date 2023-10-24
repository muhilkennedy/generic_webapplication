import { NgModule } from "@angular/core";
import { RouterModule } from "@angular/router";
import { CommonModule } from "@angular/common";
import { FormsModule, ReactiveFormsModule } from "@angular/forms";

import { AdminLayoutRoutes } from "./admin-layout.routing";
import { DashboardComponent } from "../../pages/dashboard/dashboard.component";

import { NgbModule } from "@ng-bootstrap/ng-bootstrap";
import { MaterialModule } from "../../material.module";
import { ManageuserComponent } from "src/app/pages/manageuser/manageuser.component";
import { TranslateModule } from "@ngx-translate/core";
import { ManagecustomerComponent } from '../../pages/managecustomer/managecustomer.component';
import { ItineraryComponent } from '../../pages/itinerary/itinerary.component';
import { EnquiryComponent } from '../../pages/enquiry/enquiry.component';
import { DestinationsComponent } from '../../pages/destinations/destinations.component';
import { NgxLoadingModule, ngxLoadingAnimationTypes } from "ngx-loading";
import { MatSelectModule } from "@angular/material/select";
import { MatSlideToggleModule } from "@angular/material/slide-toggle";
import { NgImageSliderModule } from 'ng-image-slider';
import {MatProgressBarModule} from '@angular/material/progress-bar';
import { MatSliderModule } from "@angular/material/slider";
import { TouristattactionsComponent } from '../../pages/touristattactions/touristattactions.component';


@NgModule({
  imports: [
    CommonModule,
    RouterModule.forChild(AdminLayoutRoutes),
    FormsModule,
    ReactiveFormsModule,
    NgbModule,
    MaterialModule,
    TranslateModule,
    MatSlideToggleModule,
    MatSelectModule,
    MatProgressBarModule,
    MatSliderModule,
    NgxLoadingModule.forRoot({
      animationType: ngxLoadingAnimationTypes.pulse,
      backdropBackgroundColour: 'rgba(0,0,0,0.3)',
      backdropBorderRadius: '4px',
      primaryColour: '#FE980F',
      secondaryColour: 'chocolate',
      tertiaryColour: 'darkred'}),
    NgImageSliderModule
  ],
  declarations: [
    DashboardComponent,
    ManageuserComponent,
    ManagecustomerComponent,
    ItineraryComponent,
    EnquiryComponent,
    DestinationsComponent,
    TouristattactionsComponent
  ],
  providers:[

  ]
})
export class AdminLayoutModule {}
