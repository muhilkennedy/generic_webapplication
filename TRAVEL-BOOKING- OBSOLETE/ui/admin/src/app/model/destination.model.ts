export class Destination {

  name: string;
  gmap: string;
  description: string;
  rootId: number;
  customerRating: string;
  rating: string;
  picture: string;
  destinationDetail: object;
  destinationSeasons: any[];
  destinationFileBlobs: any[];

  constructor() { }

  set Name(name: string){
    this.name = name;
  }

  get Name(){
    return this.name;
  }

}
