import {Component, OnInit} from '@angular/core';
import {ServiceUserService} from "../service-user.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit{

  constructor(private serviceUserService: ServiceUserService, private router:Router) {
  }

  onLogout()
  {
    this.serviceUserService.logout();
    this.router.navigateByUrl("/login");
  }

  ngOnInit() {
  }

}
