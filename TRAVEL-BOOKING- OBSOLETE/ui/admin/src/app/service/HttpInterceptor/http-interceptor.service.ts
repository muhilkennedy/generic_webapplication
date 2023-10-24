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
      if (req.url.includes("/assets/i18n/")){
        url = req.url;
      }
      let newHeaders = req.headers;
      // Append token to all request
      newHeaders = newHeaders.append('Authorization', 'Bearer ' + this.cookieService.get("X-Token"));

      const authReq = req.clone({headers: newHeaders, url: url});
      return next.handle(authReq);
      // return next.handle(authReq).pipe(
      //   // Do custom work here
      // );
  }
}
