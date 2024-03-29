import { Component, OnInit } from "@angular/core";
import { TranslateService } from "@ngx-translate/core";
import { $ } from "protractor";

declare interface RouteInfo {
  path: string;
  title: string;
  icon: string;
  class: string;
}
export const ROUTES: RouteInfo[] = [
  {
    path: "/dashboard",
    title: "Dashboard",
    icon: "icon-chart-pie-36",
    class: ""
  },
  {
    path: "/manageuser",
    title: "Manage Admin Users",
    icon: "icon-single-02",
    class: ""
  },
  {
    path: "/managecustomer",
    title: "Manage Customers",
    icon: "icon-single-02",
    class: ""
  },
  {
    path: "/itinerary",
    title: "Itineraries",
    icon: "icon-world",
    class: ""
  },
  {
    path: "/enquiry",
    title: "Enquiry",
    icon: "icon-chat-33",
    class: ""
  },
  {
    path: "/destination",
    title: "Destinations",
    icon: "icon-square-pin",
    class: ""
  },
  {
    path: "/attraction",
    title: "Tourist Attractions",
    icon: "icon-square-pin",
    class: ""
  }
];

@Component({
  selector: "app-sidebar",
  templateUrl: "./sidebar.component.html",
  styleUrls: ["./sidebar.component.css"]
})
export class SidebarComponent implements OnInit {
  menuItems: any[];

  constructor(public translate: TranslateService) {}

  ngOnInit() {
    this.menuItems = ROUTES.filter(menuItem => menuItem);//.map(menuItem1 => menuItem1.title = $localize`${menuItem1.title}`);
  }
  isMobileMenu() {
    if (window.innerWidth > 991) {
      return false;
    }
    return true;
  }
}
