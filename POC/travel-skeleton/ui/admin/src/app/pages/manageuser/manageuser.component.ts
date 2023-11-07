import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { MatSlideToggleChange } from '@angular/material/slide-toggle';
import { CookieService } from 'ngx-cookie-service';
import { ToastrService } from 'ngx-toastr';
import { RegisterRequest } from 'src/app/model/register.request.model';
import { UserService } from 'src/app/service/user/user.service';
import { environment } from 'src/environments/environment';
import { get } from 'scriptjs';
import { Observable, Subscription, observable } from 'rxjs';
import {EventSourcePolyfill} from 'ng-event-source';
import { User } from 'src/app/model/user.model';

declare var grecaptcha: any;

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

  userlist: any[] = new Array();

  selectedRealm: string = '';
  realms: any[];

  captchaError: boolean = false;

  private sseStream: Subscription;

  constructor(private http: HttpClient,private toastr: ToastrService, private cookieService: CookieService, private userService: UserService)
  {

  }

  ngOnInit(): void {
    //we need to load this dynamically to make sure captcha container is rendered along with js load.
    get("https://www.google.com/recaptcha/api.js", () => {
        console.log("Recaptcha loaded");
    });

    //we need to call event stream api with header config separately.
    let eventSource = new EventSourcePolyfill('http://localhost:8080/travel/employee/fetch', {headers: { Authorization: `Bearer ${this.cookieService.get("X-Token")}` }});
    eventSource.onmessage = (message => {
      this.userlist.push(JSON.parse(message.data));
    });
    eventSource.onopen = (a) => {
        // Do stuff here
    };
    eventSource.onerror = (e) => {
       eventSource.close();
    }
  }

  onSave(){
    const response = grecaptcha.getResponse();
    if (response.length === 0) {
      this.captchaError = true;
      return;
    }
    const body = {
      email: this.emailId,
      firstName: this.fname,
      lastName: this.lname,
      mobile: this.mobile,
      password: 'dummy'
    };
    let login = new RegisterRequest();
    login.email = this.emailId;
    login.password = 'dummy';
    login.firstName = this.fname;
    login.lastName = this.lname;
    login.mobile = this.mobile;
    login.recaptchaResponse = response;
    this.http.post<any>('/employee/create', login,
    {
    })
      .subscribe(
        (resp:any) => {
          this.toastr.success('<span class="tim-icons icon-bell-55" [data-notify]="icon"></span> <b>Admin user ' + this.fname + ' added successfully</b>.', '', {
            disableTimeOut: false,
            closeButton: true,
            enableHtml: true,
            toastClass: "alert alert-success alert-with-icon",
            positionClass: 'toast-' + 'bottom' + '-' +  'center'
          });
          grecaptcha.reset();
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
            grecaptcha.reset();
        }
      );
  }

  public toggleActive(event: MatSlideToggleChange, rootId: string) {
    this.userlist.filter(user => user.rootId == rootId).map(user => user.active = event.checked);
    this.http.patch<any>('/employee/togglestatus?rootId='+rootId,
    {})
      .subscribe(
        (resp:any) => {
          this.toastr.success('<span class="tim-icons icon-bell-55" [data-notify]="icon"></span> <b>Admin user status updated</b>.', '', {
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
