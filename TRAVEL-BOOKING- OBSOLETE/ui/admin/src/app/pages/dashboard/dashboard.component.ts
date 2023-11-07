import { FlatTreeControl } from "@angular/cdk/tree";
import { HttpClient } from "@angular/common/http";
import { Component, OnInit } from "@angular/core";
import { MatSlideToggleChange } from "@angular/material/slide-toggle";
import { MatTreeFlatDataSource, MatTreeFlattener } from "@angular/material/tree";
import Chart from 'chart.js';
import { CookieService } from "ngx-cookie-service";
import { ToastrService } from "ngx-toastr";
import { DestinationService } from "src/app/service/Destination/destination.service";
import { UserService } from "src/app/service/user/user.service";
import { environment } from "src/environments/environment";

let TREE_DATA: DestinationNode[] = new Array();

interface DestinationNode {
  name: string;
  rootId: number;
  children?: DestinationNode[];
}

/** Flat node with expandable and level information */
interface FlatNode {
  expandable: boolean;
  name: string;
  rootId: number;
  level: number;
}

@Component({
  selector: "app-dashboard",
  templateUrl: "dashboard.component.html",
  styleUrls: ['./dashboard.component.scss']
})
export class DashboardComponent implements OnInit {

  treeControl = new FlatTreeControl<FlatNode>(
    node => node.level,
    node => node.expandable,
  );

  private _transformer = (node: DestinationNode, level: number) => {
    return {
      expandable: !!node.children && node.children.length > 0,
      name: node.name,
      rootId: node.rootId,
      level: level,
    };
  };
  treeFlattener = new MatTreeFlattener(
    this._transformer,
    node => node.level,
    node => node.expandable,
    node => node.children,
  );

  dataSource = new MatTreeFlatDataSource(this.treeControl, this.treeFlattener);

  hasChild = (_: number, node: FlatNode) => node.expandable;

  loading: boolean = false;

  constructor(private destinationService: DestinationService, private toaster: ToastrService) {

  }

  ngOnInit() {
    this.loading = true;
    this.destinationService.getAllDestinationsHierarchy().subscribe({
      next: (resp: any) => {
        TREE_DATA = resp.dataList;
        this.dataSource.data = TREE_DATA;
      },
      error: (err) => {
        this.toaster.error("failed to load destinations");
      },
      complete: ()=>{
        this.loading = false;
      }
    })
  }

  loadDestination(destinationId){
    //redirect or open destination page
    alert(destinationId.rootId);
  }

}
