import {Component, OnInit} from '@angular/core';
import {ServiceUserService} from "../service-user.service";

@Component({
  selector: 'app-list-users',
  templateUrl: './list-users.component.html',
  styleUrls: ['./list-users.component.css']
})
export class ListUsersComponent implements OnInit{


  users: any=[]

  constructor(private serviceUser: ServiceUserService) {
  }

  afficherUsers()
  {
    this.serviceUser.getAllUsers().subscribe(
      res =>{
        console.log(res)
        this.users=res
      },
      err =>{
        console.log(err)
      }
    );

  }
  SupprimerUser(id:any,index :number)
  {
    this.serviceUser.deleteUser(id).subscribe(
      res =>{
        console.log(res)
        this.users.split(index,1)
      },
      err =>{
        console.log(err)
      }
    )
  }

  ngOnInit() {
   this.afficherUsers()
  }

}
