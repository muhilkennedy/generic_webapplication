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
import { TenantService } from "./service/Tenant/tenant.service";
import { HttpInterceptorService } from "./service/HttpInterceptor/http-interceptor.service";
import { LoginComponent } from "./pages/login/login.component";
import { CommonModule } from "@angular/common";
import { CookieService } from "ngx-cookie-service";
import { MaterialModule} from './material.module';
import { I18nModule } from './i18n/i18n.module';
import { TranslateService } from "@ngx-translate/core";

@Injectable()
export class TenantInitializer {

  constructor(private http: HttpClient, private tenantStore: TenantService) { }

  initializeApp(): Promise<any> {
    return new Promise((resolve, reject) => {
          console.log(`initializeApp:: Setting up Tenant`);
          this.http.get('/tenant/ping')
              .subscribe(
                (resp:any) => {
                  this.tenantStore.tenantId = resp.data.tenantId;
                  this.tenantStore.tenantActive = resp.data.active;
                  this.tenantStore.tenantName = resp.data.tenantName;
                  //load app only if tenant is active.
                  if(this.tenantStore.tenantActive){
                     resolve(true);
                  }
                  else{
                    alert("Tenant not Active! Please contact support!")
                  }
                },
                (error:any) => {
                    console.log("error in loading tenant");
                    alert("Tenant Server not Reachable at the moment! Please try again later!");
                }
              );
    });
  }
}

export function init_tenant(initializer: TenantInitializer) {
  return () => initializer.initializeApp();
}


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
    I18nModule
  ],
  declarations: [AppComponent, AdminLayoutComponent, LoginComponent],
  providers: [
    TenantInitializer,
    // {
    //   provide: APP_INITIALIZER,
    //   useFactory: init_tenant,
    //   multi: true,
    //   deps: [TenantInitializer]
    // },
    {
      provide: HTTP_INTERCEPTORS,
      useClass: HttpInterceptorService,
      multi: true
    },
    {
      provide: LOCALE_ID,
      useValue: 'de'
    },
    CookieService
  ],
  bootstrap: [AppComponent]
})
export class AppModule {}
