import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class DestinationService {

  constructor(private http: HttpClient) { }

  getAllDestinations(): Observable<any>{
    return this.http.get<any>('/destination/all');
  }

  getAllDestinationsHierarchy(): Observable<any>{
    return this.http.get<any>('/destination/tree');
  }

  createDestination(body): Observable<any>{
    return this.http.post<any>('/admin/destination/create', body);
  }

  uploadDestinationPicture(file, destinationId): Observable<any>{
    const uploadData = new FormData();
    uploadData.append("picture", file);
    uploadData.append("id", destinationId);
    return this.http.post<any>('/admin/destination/picture', uploadData);
  }

  uploadDestinationPictures(files: any[], destinationId): Observable<any>{
    const uploadData = new FormData();
    files.forEach(file => {
      uploadData.append("pictures", file);
    })
    uploadData.append("id", destinationId);
    return this.http.post<any>('/admin/destination/pictures', uploadData);
  }

  createorUpdateDestinationSeason(body, parameters): Observable<any>{
    return this.http.post<any>('/admin/destination/season', body, {params : parameters});
  }


}
