import { HttpClient, HttpHeaders, HttpResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';
import { CookieService } from 'ngx-cookie-service';
import { UserService } from 'src/app/service/user/user.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

  public email: string;
  public password: string;

  constructor(private http: HttpClient,
              private route: Router,
              private userService: UserService,
              private cookieService: CookieService,
              public translate: TranslateService) { }

  ngOnInit(): void {
  }

  login(){
    const body = {
       email: this.email,
       password: this.password
    };
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
      }),

    };
    console.log(body);
    this.http.post<any>('/user/employee/login', body, {observe: 'response'})
        .subscribe(
          {
            next: (resp:HttpResponse<any>) => {
              this.userService.userEmail = resp.body.data.emailId;
              this.userService.userId = resp.body.data.rootId;
              this.userService.userName = resp.body.data.fName + resp.body.data.lname;
              //this.userService.token = resp.headers.get('Token');
              this.userService.token = resp.headers.get("X-Token");;
              this.cookieService.set("X-Token", this.userService.token);
              this.route.navigate(['/dashboard']);
            },
            error: (err) => {
              console.log("error in loading tenant");
              alert("Invalid Creds!");
            },
            complete: () => {
              console.log('complete');
            }
          }
        );
  }

}
