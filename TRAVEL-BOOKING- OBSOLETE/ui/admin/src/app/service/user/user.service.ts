import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private _userName: string;
  private _userEmail: string;
  private _userId: string;
  private _userActive: boolean;
  private _profilePic: string;

  private _token: string;

  constructor() { }

  set userName(name: string){
    this._userName = name;
  }

  set userId(id: string){
    this._userId = id;
  }

  set userEmail(email: string){
    this._userEmail = email;
  }

  set userActive(active: boolean){
    this._userActive = active;
  }

  set token(token: string){
    this._token = token;
  }

  get token(){
    return this._token;
  }

  get userName(){
    return this._userName;
  }

  get userEmail(){
    return this._userEmail;
  }

  get isActive(){
    return this._userActive;
  }

  get userId(){
    return this._userId;
  }

  set profilePic(pic: string){
    this._profilePic = pic;
  }

  get profilePic(){
    return this._profilePic;
  }

}
