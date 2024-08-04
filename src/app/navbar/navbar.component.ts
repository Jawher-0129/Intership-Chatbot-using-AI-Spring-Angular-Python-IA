import {Component, OnInit} from '@angular/core';
import {ServiceUserService} from "../service-user.service";
import {Router} from "@angular/router";
import {SessionService} from "../session.service";
import {User} from "../user.model";

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit{
  _user: User  | null = null;


  constructor(private serviceUserService: ServiceUserService, private router:Router,private sessionService: SessionService) {
  }

  getUserFromLocalStorage(): User | null {
    const userJson = localStorage.getItem('user');
    return userJson ? JSON.parse(userJson) : null;
  }


  onLogout()
  {
    this.serviceUserService.logout();
    this.router.navigateByUrl("/login");
  }

  ngOnInit() {
    this._user = this.sessionService.getUser() || this.getUserFromLocalStorage();
    console.log(this._user);
  }

}
