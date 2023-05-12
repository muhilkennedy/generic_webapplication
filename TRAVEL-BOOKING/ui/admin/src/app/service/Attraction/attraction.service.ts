import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AttractionService {

  constructor(private http: HttpClient) { }

  createAttraction(body, parameters): Observable<any>{
    return this.http.post<any>('/admin/attraction/create', body, {params: parameters});
  }

  getAttractionsForDestination(parameters){
    return this.http.get<any>('/attraction/all', {params: parameters});
  }

  uploadAttractionPicture(file, attractionId): Observable<any>{
    const uploadData = new FormData();
    uploadData.append("picture", file);
    uploadData.append("id", attractionId);
    return this.http.post<any>('/admin/attraction/picture', uploadData);
  }

  uploadAttractionPictures(files: any[], destinationId): Observable<any>{
    const uploadData = new FormData();
    files.forEach(file => {
      uploadData.append("pictures", file);
    })
    uploadData.append("id", destinationId);
    return this.http.post<any>('/admin/attraction/pictures', uploadData);
  }

  createorUpdateAttractionSeason(body, parameters): Observable<any>{
    return this.http.post<any>('/admin/attraction/season', body, {params : parameters});
  }

}
