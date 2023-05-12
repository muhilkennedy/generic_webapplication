import { DOCUMENT } from "@angular/common";
import { HttpClient } from "@angular/common/http";
import { AfterViewInit, Component, Inject, OnInit } from "@angular/core";
import { Router } from "@angular/router";
import { CookieService } from "ngx-cookie-service";
import { UserService } from "src/app/service/user/user.service";
import { environment } from "src/environments/environment";

@Component({
  selector: "app-admin-layout",
  templateUrl: "./admin-layout.component.html",
  styleUrls: ["./admin-layout.component.scss"]
})
export class AdminLayoutComponent implements OnInit, AfterViewInit {
  public sidebarColor: string = "red";
  public modeClass: string = "disabled"
  public defaultModeCss: string;
  angularStylePath = "../../../assets/scss/angular-material/white-material.scss";

  constructor(private route: Router, private userService: UserService, private cookieService: CookieService,
              private http: HttpClient, @Inject(DOCUMENT) private document: Document) {
    if(cookieService.get("X-Token") == undefined || cookieService.get("X-Token") == null || cookieService.get("X-Token") == '' ){
      this.route.navigate(['/login']);
    }
    else{
      this.http.get('/employee/ping')
      .subscribe(
        (resp:any) => {
          this.userService.userEmail = resp.data.emailId;
          this.userService.userId = resp.data.rootId;
          this.userService.userName = resp.data.fName + " " + resp.data.lName;
          this.userService.profilePic = resp.data.profilePic;
        },
        (error:any) => {
          this.cookieService.deleteAll();
          this.route.navigate(['/login']);
        }
      );
    }
  }

  public isDarkModeEnabled(){
    return this.defaultModeCss.match("black-material.css");
  }

  changeSidebarColor(color){
    var sidebar = document.getElementsByClassName('sidebar')[0];
    var mainPanel = document.getElementsByClassName('main-panel')[0];

    this.sidebarColor = color;

    if(sidebar != undefined){
        sidebar.setAttribute('data',color);
    }
    if(mainPanel != undefined){
        mainPanel.setAttribute('data',color);
    }
  }

  changeDashboardColor(color){
    var body = document.getElementsByTagName('body')[0];
    let themeLink = this.document.getElementById(
      'client-theme'
    ) as HTMLLinkElement;
    let href:string = themeLink.href;
    if(href.includes('black-material.css')){
      this.setMaterialStyle('white-material.css');
    }
    else{
      this.setMaterialStyle('black-material.css');
    }

    if (body && color === 'white-content') {
        body.classList.add(color);
    }
    else if(body.classList.contains('white-content')) {
      body.classList.remove('white-content');
    }
  }

  ngOnInit() {
    this.setMaterialStyle("black-material.css");
  }

  setMaterialStyle(styleName: string){
    const head = this.document.getElementsByTagName('head')[0];
    this.defaultModeCss = styleName;
    let themeLink = this.document.getElementById(
      'client-theme'
    ) as HTMLLinkElement;
    if (themeLink) {
      themeLink.href = `${styleName}`;
    } else {
      const style = this.document.createElement('link');
      style.id = 'client-theme';
      style.rel = 'stylesheet';
      style.href = `${styleName}`;
      head.appendChild(style);
    }
  }

  ngAfterViewInit() {
    this.registerDragElement();
  }

  private registerDragElement() {
    const elmnt = document.getElementById('mydiv');

    let pos1 = 0, pos2 = 0, pos3 = 0, pos4 = 0;

    const dragMouseDown = (e) => {
      e = e || window.event;
      // get the mouse cursor position at startup:
      pos3 = e.clientX;
      pos4 = e.clientY;
      document.onmouseup = closeDragElement;
      // call a function whenever the cursor moves:
      document.onmousemove = elementDrag;
    };

    const elementDrag = (e) => {
      e = e || window.event;
      // calculate the new cursor position:
      pos1 = pos3 - e.clientX;
      pos2 = pos4 - e.clientY;
      pos3 = e.clientX;
      pos4 = e.clientY;
      // set the element's new position:
      elmnt.style.top = elmnt.offsetTop - pos2 + 'px';
      elmnt.style.left = elmnt.offsetLeft - pos1 + 'px';
    };

    const closeDragElement = () => {
      /* stop moving when mouse button is released:*/
      document.onmouseup = null;
      document.onmousemove = null;
    };

    if (document.getElementById(elmnt.id + 'header')) {
      /* if present, the header is where you move the DIV from:*/
      document.getElementById(elmnt.id + 'header').onmousedown = dragMouseDown;
    } else {
      /* otherwise, move the DIV from anywhere inside the DIV:*/
      elmnt.onmousedown = dragMouseDown;
    }
  }

  public allowDrop(ev): void {
    ev.preventDefault();
  }

  public drag(ev): void {
    ev.dataTransfer.setData("text", ev.target.id);
  }

  public drop(ev): void {
    ev.preventDefault();
    var data = ev.dataTransfer.getData("text");
    ev.target.appendChild(document.getElementById(data));
  }

}
