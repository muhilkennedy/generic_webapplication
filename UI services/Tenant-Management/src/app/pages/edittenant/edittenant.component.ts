import { DatePipe } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
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

  selectedRealm;
  realms: any[];

  isRealmSelected = false;

  public renewalDate;
  public expiryDate;
  public datePipe:DatePipe;

  subscriptions: any[];
  currentValidity: string;

  range:FormGroup = new FormGroup({
    start: new FormControl(new Date()),
    end: new FormControl(this.addOneYear()),
  });

  addOneYear() {
    // Making a copy with the Date() constructor
    let dateCopy = new Date();
    dateCopy.setFullYear(dateCopy.getFullYear() + 1);
    return dateCopy;
  }

  constructor(private http: HttpClient,
              private toastr: ToastrService,
              private cookieService: CookieService,
              private userService: UserService)
  {
    this.datePipe = new DatePipe('en-GB');
  }

  ngOnInit(): void {
    this.http.get(environment.backendBaseUrl + '/tenantresource/alltenants', {
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
    this.http.get(environment.backendBaseUrl + '/tenantresource/subscriptions?tenantUniqueName='+realmUniqueName.tenantUniqueName, {
      headers: {
        'X-Tenant': environment.tenantId,
        'Accept-Language': 'en_US',
        'Authorization': 'Bearer ' + this.cookieService.get('X-Token')
      }})
        .subscribe(
          (resp:any) => {
            this.subscriptions = resp.dataList;
          },
          (error:any) => {
              console.log("error in loading tenant");
          }
        );
  }

  onRenew(){
    const body = {
        renewalDate: this.datePipe.transform(this.range.value.start, 'yyyy-MM-dd'),
        expiryDate: this.datePipe.transform(this.range.value.end, 'yyyy-MM-dd')
   };
    this.http.post<any>(environment.backendBaseUrl+'/tenantresource/subscriptions?tenantUniqueName='+this.selectedRealm.tenantUniqueName, body,
      {
      headers: {
        'X-Tenant': environment.tenantId,
        'Accept-Language': 'en_US',
        'Authorization': 'Bearer ' + this.cookieService.get('X-Token')
      }})
        .subscribe(
          (resp:any) => {
            this.subscriptions = resp.dataList;
            this.toastr.success('<span class="tim-icons icon-bell-55" [data-notify]="icon"></span> <b>Tenant Renewal successful</b>.', '', {
              disableTimeOut: false,
              closeButton: true,
              enableHtml: true,
              toastClass: "alert alert-success alert-with-icon",
              positionClass: 'toast-' + 'bottom' + '-' +  'center'
            });
          },
          (error:any) => {
              console.log("error in loading tenant");
              this.toastr.error('<span class="tim-icons icon-bell-55" [data-notify]="icon"></span> <b>Error Renewing tenant : ' + error.error.message +'</b>.', '', {
                disableTimeOut: false,
                closeButton: true,
                enableHtml: true,
                toastClass: "alert alert-error alert-with-icon",
                positionClass: 'toast-' + 'bottom' + '-' +  'center'
              });
          }
        );
  }

}
