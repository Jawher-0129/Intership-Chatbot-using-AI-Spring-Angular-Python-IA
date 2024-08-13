import { Injectable } from '@angular/core';
import {User} from "./user.model";

@Injectable({
  providedIn: 'root'
})
export class SessionService {

   _user: User  | null = null;

  setUser(user: User): void {
    this._user = user;
    localStorage.setItem('user', JSON.stringify(user));

  }

  getUser(): User | null {
    if (!this._user) {
      const userJson = localStorage.getItem('user');
      this._user = userJson ? JSON.parse(userJson) : null;
    }
    return this._user;
  }

  isLoggedIn(): boolean {
    return this._user !== null;
  }


  constructor() { }



}
