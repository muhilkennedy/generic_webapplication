import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';

import { CalloutModule, NavModule, SpinnerModule, TabsModule, UtilitiesModule } from '@coreui/angular';
import { IconModule } from '@coreui/icons-angular';
import { ErrorComponent } from './error/error.component';
import { NotFoundComponent } from './notfound/notfound.component';
import { SpinnerComponent } from './spinner/spinner.component';
import { MaterialModule } from 'src/app/material.module';
import { FormsModule } from '@angular/forms';

@NgModule({
  declarations: [
    ErrorComponent,
    NotFoundComponent,
    SpinnerComponent
  ],
  exports: [
    ErrorComponent,
    NotFoundComponent,
    SpinnerComponent
  ],
  imports: [
    CommonModule,
    NavModule,
    IconModule,
    RouterModule,
    TabsModule,
    UtilitiesModule,
    CalloutModule,
    SpinnerModule,
    MaterialModule,
    FormsModule
  ]
})
export class ComponentsModule {
}
