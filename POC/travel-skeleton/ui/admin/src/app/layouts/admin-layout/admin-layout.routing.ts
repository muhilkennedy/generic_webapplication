import { Routes } from "@angular/router";
import { ManageuserComponent } from "src/app/pages/manageuser/manageuser.component";

import { DashboardComponent } from "../../pages/dashboard/dashboard.component";
// import { RtlComponent } from "../../pages/rtl/rtl.component";

export const AdminLayoutRoutes: Routes = [
  { path: "dashboard", component: DashboardComponent },
  { path: "manageuser", component: ManageuserComponent }
];
