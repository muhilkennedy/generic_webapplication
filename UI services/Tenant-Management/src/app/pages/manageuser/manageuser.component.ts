import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { CookieService } from 'ngx-cookie-service';
import { ToastrService } from 'ngx-toastr';
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

  constructor(private http: HttpClient,private toastr: ToastrService, private cookieService: CookieService) { }

  ngOnInit(): void {
  }

  onSave(){
    const body = {
      email: this.emailId,
      firstName: this.fname,
      lastName: this.lname,
      mobile: this.mobile,
      password: 'dummy'
    };
    this.http.post<any>(environment.backendBaseUrl+'/user/auth/employee/create', body,
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

}
