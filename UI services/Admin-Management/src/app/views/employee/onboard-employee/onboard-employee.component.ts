import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-onboard-employee',
  templateUrl: './onboard-employee.component.html',
  styleUrls: ['./onboard-employee.component.scss']
})
export class OnboardEmployeeComponent implements OnInit {
  ngOnInit(): void {
    console.log("onboard init");
  }

}
