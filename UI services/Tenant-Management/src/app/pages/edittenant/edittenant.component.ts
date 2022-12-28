import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { CookieService } from 'ngx-cookie-service';
import { ToastrService } from 'ngx-toastr';
import { UserService } from 'src/app/service/user/user.service';
import { environment } from 'src/environments/environment';

@Component({
  selector: 'app-edittenant',
  templateUrl: './edittenant.component.html',
  styleUrls: ['./edittenant.component.scss']
})
export class EdittenantComponent implements OnInit {

  selectedRealm: string = '';
  realms: any[];

  isRealmSelected = false;

  constructor(private http: HttpClient,
              private toastr: ToastrService,
              private cookieService: CookieService,
              private userService: UserService) { }

  ngOnInit(): void {
    this.http.get(environment.backendBaseUrl + '/tenant/alltenants', {
      headers: {
        'X-Tenant': environment.tenantId,
        'Accept-Language': 'en_US',
        'Authorization': 'Bearer ' + this.cookieService.get('X-Token')
      }})
        .subscribe(
          (resp:any) => {
            this.realms = resp.dataList;
          },
          (error:any) => {
              console.log("error in loading tenant");
          }
        );
  }

  getRealmData(realmUniqueName){
    this.isRealmSelected =  true;
  }

}
