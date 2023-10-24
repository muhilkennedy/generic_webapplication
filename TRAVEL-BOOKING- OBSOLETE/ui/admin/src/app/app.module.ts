import { BrowserAnimationsModule } from "@angular/platform-browser/animations";
import { APP_INITIALIZER, Injectable, LOCALE_ID, NgModule } from "@angular/core";
import { registerLocaleData } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from "@angular/forms";
import { HttpClient, HttpClientModule, HTTP_INTERCEPTORS } from "@angular/common/http";
import { RouterModule } from "@angular/router";
import { ToastrModule } from 'ngx-toastr';

import { AppComponent } from "./app.component";
import { AdminLayoutComponent } from "./layouts/admin-layout/admin-layout.component";

import { NgbModule } from "@ng-bootstrap/ng-bootstrap";

import { AppRoutingModule } from "./app-routing.module";
import { ComponentsModule } from "./components/components.module";
import { HttpInterceptorService } from "./service/HttpInterceptor/http-interceptor.service";
import { LoginComponent } from "./pages/login/login.component";
import { CommonModule } from "@angular/common";
import { CookieService } from "ngx-cookie-service";
import { MaterialModule} from './material.module';
import { I18nModule } from './i18n/i18n.module';
import { TranslateService } from "@ngx-translate/core";
import { MatSlideToggleModule } from '@angular/material/slide-toggle';
import { MatSelectModule } from '@angular/material/select';
import {MatProgressSpinnerModule} from '@angular/material/progress-spinner';
import { NgxLoadingModule, ngxLoadingAnimationTypes } from "ngx-loading";


@NgModule({
  imports: [
    BrowserAnimationsModule,
    FormsModule,
    HttpClientModule,
    ComponentsModule,
    NgbModule,
    RouterModule,
    AppRoutingModule,
    ReactiveFormsModule,
    CommonModule,
    ToastrModule.forRoot(),
    MaterialModule,
    I18nModule,
    MatSlideToggleModule,
    MatSelectModule,
    MatProgressSpinnerModule,
    NgxLoadingModule.forRoot({
      animationType: ngxLoadingAnimationTypes.cubeGrid,
      backdropBackgroundColour: 'rgba(0,0,0,0.3)',
      backdropBorderRadius: '4px',
      primaryColour: '#FE980F',
      secondaryColour: 'chocolate',
      tertiaryColour: 'darkred'
    }),
  ],
  declarations: [AppComponent, AdminLayoutComponent, LoginComponent],
  providers: [
    {
      provide: HTTP_INTERCEPTORS,
      useClass: HttpInterceptorService,
      multi: true
    },
    {
      provide: LOCALE_ID,
      useValue: 'en_US'
    },
    CookieService
  ],
  bootstrap: [AppComponent]
})
export class AppModule {}
