import { NgModule } from "@angular/core";
import { HttpClientModule } from "@angular/common/http";
import { RouterModule } from "@angular/router";
import { CommonModule } from "@angular/common";
import { FormsModule, ReactiveFormsModule } from "@angular/forms";

import { AdminLayoutRoutes } from "./admin-layout.routing";
import { DashboardComponent } from "../../pages/dashboard/dashboard.component";

import { NgbModule } from "@ng-bootstrap/ng-bootstrap";
import { OnboardComponent } from "src/app/pages/onboard/onboard.component";
import { EdittenantComponent } from "src/app/pages/edittenant/edittenant.component";
import { MaterialModule } from "../../material.module";
import { ManageuserComponent } from "src/app/pages/manageuser/manageuser.component";
import { TranslateModule } from "@ngx-translate/core";

@NgModule({
  imports: [
    CommonModule,
    RouterModule.forChild(AdminLayoutRoutes),
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule,
    NgbModule,
    MaterialModule,
    TranslateModule
  ],
  declarations: [
    DashboardComponent,
    OnboardComponent,
    EdittenantComponent,
    ManageuserComponent
  ],
  providers:[

  ]
})
export class AdminLayoutModule {}
