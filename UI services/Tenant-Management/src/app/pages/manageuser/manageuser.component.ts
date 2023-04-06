import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { MatSlideToggleChange } from '@angular/material/slide-toggle';
import { CookieService } from 'ngx-cookie-service';
import { ToastrService } from 'ngx-toastr';
import { UserService } from 'src/app/service/user/user.service';
import { environment } from 'src/environments/environment';

@Component({
  selector: 'app-manageuser',
  templateUrl: './manageuser.component.html',
  styleUrls: ['./manageuser.component.scss']
})
export class ManageuserComponent implements OnInit {

  fname: string;
  lname: string;
  emailId: string;
  mobile: string;

  userlist: any[];

  selectedRealm: string = '';
  realms: any[];

  constructor(private http: HttpClient,private toastr: ToastrService, private cookieService: CookieService, private userService: UserService) { }

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
    this.http.get(environment.backendBaseUrl+'/user/employee/fetch',
    {
      headers: {
        'X-Tenant': environment.tenantId,
        'Accept-Language': 'en_US',
        'Authorization': 'Bearer ' + this.cookieService.get('X-Token')
    }})
      .subscribe(
        (resp:any) => {
          this.userlist = resp.dataList;
        },
        (error:any) => {
          console.log("error in loading users");
        }
      );
  }

  onSave(){
    const body = {
      email: this.emailId,
      firstName: this.fname,
      lastName: this.lname,
      mobile: this.mobile,
      password: 'dummy'
    };
    this.http.post<any>(environment.backendBaseUrl+'/user/employee/create?tenantUniqueName='+this.selectedRealm, body,
    {
    headers: {
      'X-Tenant': environment.tenantId,
      'Accept-Language': 'en_US',
      'Authorization': 'Bearer ' + this.cookieService.get('X-Token')
    }})
      .subscribe(
        (resp:any) => {
          this.toastr.success('<span class="tim-icons icon-bell-55" [data-notify]="icon"></span> <b>Admin user ' + this.fname + ' added successfully</b>.', '', {
            disableTimeOut: false,
            closeButton: true,
            enableHtml: true,
            toastClass: "alert alert-success alert-with-icon",
            positionClass: 'toast-' + 'bottom' + '-' +  'center'
          });
        },
        (error:any) => {
            console.log("error in loading tenant");
            this.toastr.error('<span class="tim-icons icon-bell-55" [data-notify]="icon"></span> <b>Error Adding user : ' + error.error.message +'</b>.', '', {
              disableTimeOut: false,
              closeButton: true,
              enableHtml: true,
              toastClass: "alert alert-error alert-with-icon",
              positionClass: 'toast-' + 'bottom' + '-' +  'center'
            });
        }
      );
  }

  public toggleActive(event: MatSlideToggleChange, rootId: string) {
    this.userlist.filter(user => user.rootId == rootId).map(user => user.active = event.checked);
    this.http.patch<any>(environment.backendBaseUrl+'/user/employee/toggleuserstatus?userId=' + rootId, {},
    {
    headers: {
      'X-Tenant': environment.tenantId,
      'Accept-Language': 'en_US',
      'Authorization': 'Bearer ' + this.cookieService.get('X-Token')
    }})
      .subscribe(
        (resp:any) => {
          this.toastr.success('<span class="tim-icons icon-bell-55" [data-notify]="icon"></span> <b>Admin user ' + this.fname + ' status updated</b>.', '', {
            disableTimeOut: false,
            closeButton: true,
            enableHtml: true,
            toastClass: "alert alert-success alert-with-icon",
            positionClass: 'toast-' + 'bottom' + '-' +  'center'
          });
        },
        (error:any) => {
            console.log("error in loading tenant");
            this.toastr.error('<span class="tim-icons icon-bell-55" [data-notify]="icon"></span> <b>Error updating user : ' + error.error.message +'</b>.', '', {
              disableTimeOut: false,
              closeButton: true,
              enableHtml: true,
              toastClass: "alert alert-error alert-with-icon",
              positionClass: 'toast-' + 'bottom' + '-' +  'center'
            });
        }
      );
  }

  public shouldDisable(userId: string){
    return userId==this.userService.userId;
  }

}
