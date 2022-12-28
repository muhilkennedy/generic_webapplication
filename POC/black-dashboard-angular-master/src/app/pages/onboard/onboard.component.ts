import { Component, OnInit } from '@angular/core';

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

  constructor() { }

  ngOnInit(): void {
  }

}
