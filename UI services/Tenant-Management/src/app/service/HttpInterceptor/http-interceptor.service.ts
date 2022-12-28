import { HttpEvent, HttpHandler, HttpInterceptor, HttpRequest } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { CookieService } from 'ngx-cookie-service';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class HttpInterceptorService implements HttpInterceptor {

  constructor(private cookieService: CookieService) { }
  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
      let url = environment.backendBaseUrl + req.url;
      let newHeaders = req.headers;
      // Append tenant-Id to all outgoing requests.
      if (environment.tenantId) {
         newHeaders = newHeaders.append('X-Tenant', environment.tenantId);
         newHeaders = newHeaders.append('Accept-Language', 'en_US');
         newHeaders = newHeaders.append('Authorization', 'Bearer ' + this.cookieService.get("X-Token"));
      }
      // Add Authorization token if present.
      // if(this.userStore!=undefined && this.userStore.JwtToken){
      //   newHeaders = newHeaders.append('Authorization', `Bearer ${this.userStore.JwtToken}`);
      // }
      // else if(this.cookieService.get('JWT') != null){
      //   newHeaders = newHeaders.append('Authorization', `Bearer ${this.cookieService.get('JWT')}`);
      // }
      const authReq = req.clone({headers: newHeaders, url: url});
      return next.handle(authReq);
      // return next.handle(authReq).pipe(
      //   // Do custom work here
      // );
  }
}
