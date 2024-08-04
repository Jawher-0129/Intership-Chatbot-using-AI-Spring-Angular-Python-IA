import {Component, OnInit} from '@angular/core';
import {ServiceUserService} from "../service-user.service";

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit{
  message :string='';

  constructor(private serviceUser: ServiceUserService) {
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
  }


}
