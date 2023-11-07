import { Component, OnInit, ViewChild } from '@angular/core';
import { ThemePalette } from '@angular/material/core';
import { ProgressBarMode } from '@angular/material/progress-bar';
import { DomSanitizer } from '@angular/platform-browser';
import { NgImageSliderComponent } from 'ng-image-slider';
// import { NgxSwiperConfig } from 'ngx-image-swiper';
import { Destination } from 'src/app/model/destination.model';
import { DestinationService } from 'src/app/service/Destination/destination.service';
import { FileUtil } from 'src/app/service/util/file-util.service';
import { MonthScaleMap, SeasonsUtil } from 'src/app/service/util/month-scale.service';

@Component({
  selector: 'app-destinations',
  templateUrl: './destinations.component.html',
  styleUrls: ['./destinations.component.scss']
})
export class DestinationsComponent implements OnInit {

  @ViewChild('nav') slider: NgImageSliderComponent;

  loading: boolean = false;
  manageDestinationLoading: boolean = false;
  manageAttractionLoading: boolean = false;

  destinationName: string;
  description: string;
  gmap: string;
  parentId: number;
  destinationPicture: File = null;
  destinationPictures: File[] = new Array();

  destinations: Destination[] = new Array();
  selectedDestination: Destination = null;
  manageDestination: Destination = null;

  embeddedDestinationUrl: any;
  destinationMonthScale: MonthScaleMap[] = new Array();
  attractionMonthScale: MonthScaleMap[] = new Array();

  scaleColor: ThemePalette = 'accent';
  scaleMode: ProgressBarMode = 'determinate';
  scaleBufferValue = 100;

  constructor(private destinationService: DestinationService, private sanitzer: DomSanitizer) { }

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

  sanitizeUrl(url){
    return this.sanitzer.bypassSecurityTrustResourceUrl(url);
  }

  setDestinationDetails(destination: Destination){
    this.embeddedDestinationUrl = this.sanitizeUrl(destination.gmap);
    this.imageObject.length = 0;
    this.destinationMonthScale.length = 0;
    if(destination.destinationFileBlobs != undefined && destination.destinationFileBlobs != null){
      destination.destinationFileBlobs.forEach(fileBlob => {
        let imageContent = {
            image: fileBlob.mediaurl,
            thumbImage: fileBlob.mediaurl,
            alt: fileBlob.rootId,
            title: fileBlob.rootId
        }
        this.imageObject.push(imageContent);
      })
    }
    if(destination.destinationSeasons != undefined && destination.destinationSeasons != null && destination.destinationSeasons.length > 0){
      destination.destinationSeasons.forEach(season => {
        let monthScale = new MonthScaleMap(season.month, season.scale);
        this.destinationMonthScale.push(monthScale);
      })
    }
    else{
      this.destinationMonthScale = SeasonsUtil.getMonthScaleMap();
    }
  }

  getMonthName(monthValue): string{
    return SeasonsUtil.getMonthName(monthValue);
  }

  setParentdestination(destination:any){
    this.selectedDestination = destination;
  }

  getSelectedDestinationName(): string{
    return this.selectedDestination != null ? this.selectedDestination.name : "";
  }

  setManageDestination(destination:any){
    this.manageDestination = destination;
  }

  getManageDestination(){
    return this.manageDestination != null? this.manageDestination.name : "";
  }

  getName(dst){
    return dst != null ? dst.name : "N/A";
  }

  handleDestinationFileSelect(files: FileList) {
    if(FileUtil.isValidPictureFile(files.item(0).name)){
      this.destinationPicture = files.item(0);
    }
    else{
      alert(files.item(0).name + " is not supported!");
    }
  }

  handleDestinationBulkFileSelect(files: FileList) {
    this.destinationPictures.length = 0;
    console.log(files.length);
    for(var i=0; i<files.length; i++){
      if(FileUtil.isValidPictureFile(files.item(i).name)){
        this.destinationPictures.push(files.item(i));
      }
      else{
        alert(files.item(i).name + " is not supported!");
      }
    }
  }

  onDestinationSave(){
    this.loading = true;
    let body = {
      name : this.destinationName,
      description : this.description,
      gmap: this.gmap,
      parentDestinationId: ((this.selectedDestination!=undefined || this.selectedDestination != null)?this.selectedDestination.rootId:null)
    }
    this.destinationService.createDestination(body).subscribe({
      next: (resp: any) => {
        this.destinationService.uploadDestinationPicture(this.destinationPicture, resp.data.rootId).subscribe({
          next: (resp: any) => {
            this.loadDestinations();
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
        console.log("error in loading tenant");
        alert(err);
      },
      complete: () =>{
        //No-Op
      }
    })
  }

  upoadDestinationPictures(){
    this.manageDestinationLoading = true;
    this.destinationService.uploadDestinationPictures(this.destinationPictures,this.manageDestination.rootId).subscribe({
      next: (resp: any) => {
        this.loadDestinations();
      },
      error: (err) => {
        console.log("error in loading tenant");
        alert(err);
      },
      complete: () =>{
        this.manageDestinationLoading = false;
      }
    })
  }

  updateDestinationSeasons(){
    this.manageDestinationLoading = true;
    this.destinationService.createorUpdateDestinationSeason(this.destinationMonthScale, {destinationId: this.manageDestination.rootId})
    .subscribe({
      next: (resp: any) => {
        this.loadDestinations();
      },
      error: (err) => {
        console.log("error in loading tenant");
        alert(err);
      },
      complete: () =>{
        this.manageDestinationLoading = false;
      }
    })
  }

  /*swiperConfig: NgxSwiperConfig = {
    navigation: true,
    navigationPlacement: 'inside',
    pagination: true,
    paginationPlacement: 'outside',
    loop: true,
    keyboardNavigation: true
  };

  images = [
    'https://images.pexels.com/photos/2387869/pexels-photo-2387869.jpeg',
    'https://images.pexels.com/photos/2395264/pexels-photo-2395264.jpeg',
    'https://images.pexels.com/photos/2474014/pexels-photo-2474014.jpeg',
    'https://images.pexels.com/photos/2440296/pexels-photo-2440296.jpeg'
  ];*/

  imageObject: Array<object> = new Array();

//   [{
//     image: 'https://images.pexels.com/photos/2387869/pexels-photo-2387869.jpeg',
//     thumbImage: 'https://images.pexels.com/photos/2387869/pexels-photo-2387869.jpeg',
//     alt: 'alt of image',
//     title: 'image1'
// }

deleteCurrentSliderImage(){
  alert("deleted" + this.slider.visiableImageIndex);
}

rating ={
  color: "primary",
  readonly: false,
  disabled: false,
  dense: true,
  value: 1,
  max: 5
}

rat = 3;
max=5;
col='primary';



}
