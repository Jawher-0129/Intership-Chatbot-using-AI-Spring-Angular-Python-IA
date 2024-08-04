import { Injectable } from '@angular/core';
import {User} from "./user.model";

@Injectable({
  providedIn: 'root'
})
export class SessionService {

   _user: User  | null = null;

  // Méthode pour définir l'utilisateur
  setUser(user: User): void {
    this._user = user;
    localStorage.setItem('user', JSON.stringify(user));

  }

  // Méthode pour obtenir les informations de l'utilisateur
  getUser(): User | null {
    if (!this._user) {
      const userJson = localStorage.getItem('user');
      this._user = userJson ? JSON.parse(userJson) : null;
    }
    return this._user;
  }

  // Méthode pour vérifier si l'utilisateur est connecté
  isLoggedIn(): boolean {
    return this._user !== null;
  }


  constructor() { }



}
