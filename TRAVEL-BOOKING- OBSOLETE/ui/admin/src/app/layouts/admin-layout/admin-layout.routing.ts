import { Routes } from "@angular/router";
import { ManageuserComponent } from "src/app/pages/manageuser/manageuser.component";

import { DashboardComponent } from "../../pages/dashboard/dashboard.component";
import { ManagecustomerComponent } from "src/app/pages/managecustomer/managecustomer.component";
import { ItineraryComponent } from "src/app/pages/itinerary/itinerary.component";
import { EnquiryComponent } from "src/app/pages/enquiry/enquiry.component";
import { DestinationsComponent } from "src/app/pages/destinations/destinations.component";
import { TouristattactionsComponent } from "src/app/pages/touristattactions/touristattactions.component";
// import { RtlComponent } from "../../pages/rtl/rtl.component";

export const AdminLayoutRoutes: Routes = [
  { path: "dashboard", component: DashboardComponent },
  { path: "manageuser", component: ManageuserComponent },
  { path: "managecustomer", component: ManagecustomerComponent },
  { path: "itinerary", component: ItineraryComponent },
  { path: "enquiry", component: EnquiryComponent },
  { path: "destination", component: DestinationsComponent },
  { path: "attraction", component: TouristattactionsComponent }
];
