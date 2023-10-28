import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { User } from 'src/app/model/user.model';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private user: User;

  constructor(private http: HttpClient) {
    this.user = new User();
  }

  getCurrentUser(): User {
    return this.user;
  }

  login(body: any): Observable<any>  {
    return this.http.post<any>(`${environment.backendProxy}/user/employee/login`, body, { observe: 'response' })
  }

  pingUser(): Observable<any> {
    return this.http.get(`${environment.backendProxy}/employee/ping`);
  }

  getAllPermissions(): Observable<any> {
    return this.http.get(`${environment.backendProxy}/role/fetch/permissions`);
  }

  createRole(body: any, tenantRootId: any): Observable<any>{
    return this.http.post<any>(`${environment.backendProxy}/role/create`, body, {
      params: {
        tenantId : tenantRootId
      }
    })
  }
  
}
