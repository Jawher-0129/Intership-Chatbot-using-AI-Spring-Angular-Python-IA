import {Component, OnInit} from '@angular/core';
import {ServiceUserService} from "../service-user.service";
import {SessionService} from "../session.service";
import {User} from "../user.model";

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit{
  message :string='';

  user: User | null = null;


  constructor(private serviceUser: ServiceUserService,private sessionService:SessionService) {
  }

  hello()
  {
    this.serviceUser.hello().subscribe(
      response =>{
        console.log(response)
        this.message=response.message
      },
      error => {
        console.log(error)
      }
    )
  }

  ngOnInit() {
    this.hello()
    this.user=this.sessionService.getUser();
    console.log(this.user);
  }




}
