import { DatePipe } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { DateAdapter } from '@angular/material/core';
import { Router } from '@angular/router';
import { CookieService } from 'ngx-cookie-service';
import { ToastrService } from 'ngx-toastr';
import { environment } from 'src/environments/environment';

@Component({
  selector: 'app-onboard',
  templateUrl: './onboard.component.html',
  styleUrls: ['./onboard.component.scss']
})
export class OnboardComponent implements OnInit {

  public tenantName: string;
  public uniqueName: string;
  public tagLine: string;
  public tenantEmail: string;
  public tenantContact: string;

  public renewalDate;
  public expiryDate;
  public datePipe:DatePipe;

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

  constructor(private http: HttpClient, private toastr: ToastrService, private cookieService: CookieService,
              private route: Router, private dateAdapter: DateAdapter<Date>) {
                this.dateAdapter.setLocale('en-GB');
                this.datePipe = new DatePipe('en-GB');
  }

  ngOnInit(): void {
  }

  onSave(){
    const body = {
      tenantName: this.tenantName,
      tenantUniqueName: this.uniqueName,
      tagLine: this.tagLine,
      tenantEmail: this.tenantEmail,
      tenantContact: this.tenantContact,
      tenantSubscription: {
        renewalDate: this.datePipe.transform(this.range.value.start, 'yyyy-MM-dd'),
        expiryDate: this.datePipe.transform(this.range.value.end, 'yyyy-MM-dd')
      }
   };
    this.http.post<any>(environment.backendBaseUrl+'/tenant/auth/create', body,
      {
      headers: {
        'X-Tenant': environment.tenantId,
        'Accept-Language': 'en_US',
        'Authorization': 'Bearer ' + this.cookieService.get('X-Token')
      }})
        .subscribe(
          (resp:any) => {
            this.toastr.success('<span class="tim-icons icon-bell-55" [data-notify]="icon"></span> <b>Tenant onboarded successfully</b>.', '', {
              disableTimeOut: false,
              closeButton: true,
              enableHtml: true,
              toastClass: "alert alert-success alert-with-icon",
              positionClass: 'toast-' + 'bottom' + '-' +  'center'
            });
            this.route.navigate(['/dashboard']);
          },
          (error:any) => {
              console.log("error in loading tenant");
              this.toastr.error('<span class="tim-icons icon-bell-55" [data-notify]="icon"></span> <b>Error Adding tenant : ' + error.error.message +'</b>.', '', {
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
