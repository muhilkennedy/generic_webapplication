import { Component, OnInit } from '@angular/core';
import { ToastrService } from 'ngx-toastr';
import { Attraction } from 'src/app/model/Attraction.model';
import { Destination } from 'src/app/model/destination.model';
import { AttractionService } from 'src/app/service/Attraction/attraction.service';
import { DestinationService } from 'src/app/service/Destination/destination.service';
import { FileUtil } from 'src/app/service/util/file-util.service';

@Component({
  selector: 'app-touristattactions',
  templateUrl: './touristattactions.component.html',
  styleUrls: ['./touristattactions.component.scss']
})
export class TouristattactionsComponent implements OnInit {

  loading: boolean = false;
  manageAttractionLoading: boolean = false;

  atrGmap:string;
  atrDescription: string;
  attractionName: string
  selectedParentDestination: Destination = null;
  attractionPicture: File = null;
  destinations: Destination[] = new Array();
  attractions: Attraction[] = new Array();

  manageDestination: Destination = null;
  manageAttraction: Attraction = null;
  attractionPictures: File[] = new Array();

  constructor(private destinationService: DestinationService, private attractionService: AttractionService, private toastr: ToastrService) { }

  ngOnInit(): void {
    this.loadDestinations();
  }

  loadDestinations() {
    this.loading = true;
    this.destinations.length = 0;
    this.destinationService.getAllDestinations().subscribe({
      next: (resp: any) => {
        resp.dataList.forEach(dest => {
          this.destinations.push(dest);
        });
      },
      error: (err) => {
        console.log("error in loading destinations");
        alert(err);
      },
      complete: () => {
        this.loading = false;
      }
    })
  }

  handleAttractionFileSelect(files: FileList) {
    if(FileUtil.isValidPictureFile(files.item(0).name)){
      this.attractionPicture = files.item(0);
    }
    else{
      alert(files.item(0).name + " is not supported!");
    }
  }

  handleAttractionBulkFileSelect(files: FileList) {
    this.attractionPictures.length = 0;
    console.log(files.length);
    for(var i=0; i<files.length; i++){
      if(FileUtil.isValidPictureFile(files.item(i).name)){
        this.attractionPictures.push(files.item(i));
      }
      else{
        alert(files.item(i).name + " is not supported!");
      }
    }
  }

  onAttractionSave(){
    this.loading = true;
    let body = {
      name : this.attractionName,
      description : this.atrDescription,
      gmap: this.atrGmap
    }
    let param = { destinationId: (this.selectedParentDestination != undefined || this.selectedParentDestination != null) ? this.selectedParentDestination.rootId : null }
    this.attractionService.createAttraction(body, param).subscribe({
      next: (resp:any)=>{
        this.attractionService.uploadAttractionPicture(this.attractionPicture, this.selectedParentDestination.rootId).subscribe({
          next: (resp: any) => {
            this.toastr.success('<span class="tim-icons icon-bell-55" [data-notify]="icon"></span> <b>Tourist Attraction Created Succesfully</b>.', '', {
              disableTimeOut: false,
              closeButton: true,
              enableHtml: true,
              toastClass: "alert alert-success alert-with-icon",
              positionClass: 'toast-' + 'bottom' + '-' +  'center'
            });
          },
          error: (err) => {
            console.log("error in loading tenant");
            alert(err);
          },
          complete: () =>{
            this.loading = false;
          }
        })
      },
      error: (err) => {
        this.toastr.error('<span class="tim-icons icon-bell-55" [data-notify]="icon"></span> <b>Tourist Attraction failed with error ' + err.error.detail + '</b>.', '', {
          disableTimeOut: false,
          closeButton: true,
          enableHtml: true,
          toastClass: "alert alert-error alert-with-icon",
          positionClass: 'toast-' + 'bottom' + '-' +  'center'
        });
      },
      complete: () =>{
      }
    })
  }

  setParentDestinationForManage(destination: Destination){
    this.manageAttractionLoading = true;
    let param = { destinationId : destination.rootId };
    this.attractions.length = 0;
    this.attractionService.getAttractionsForDestination(param).subscribe({
      next: (resp:any)=>{
        resp.dataList.forEach(data => {
          this.attractions.push(data);
        })
      },
      error: (err) => {
        alert("failed to get attractions");
      },
      complete: () =>{
        this.manageAttractionLoading = false;
      }
    })
  }

  setAttractionForManage(attraction: Attraction){

  }

}
