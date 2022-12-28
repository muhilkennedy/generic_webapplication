import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class TenantService {

  private _tenantId: string;
  private _tenantName: string;
  private _tenantActive: boolean;
  //tenant details

  constructor() {
    this._tenantId = environment.tenantId;
  }

  set tenantId(id:string){
    this._tenantId = id;
  }

  get tenantId():string{
    return this._tenantId;
  }

  set tenantName(name:string){
    this._tenantName = name;
  }

  get tenantName():string{
    return this._tenantName;
  }

  set tenantActive(active:boolean){
    this._tenantActive = active;
  }

  get tenantActive():boolean{
    return this._tenantActive;
  }
}
