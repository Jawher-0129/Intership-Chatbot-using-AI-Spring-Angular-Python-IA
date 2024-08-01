import {Component, OnInit} from '@angular/core';
import {ServiceUserService} from "../service-user.service";
import {ActivatedRoute, Router} from "@angular/router";

@Component({
  selector: 'app-update',
  templateUrl: './update.component.html',
  styleUrls: ['./update.component.css']
})
export class UpdateComponent implements OnInit{

  user : any

  id :any

  update()
  {
    this.serviceUser.updateUser(this.id,this.user).subscribe(
      res => {
        console.log(res)
        this.router.navigate(['/list'])
      },
      err =>{
        console.log(err)
      }
    )
  }
  constructor(private serviceUser:ServiceUserService,private act: ActivatedRoute,private router:Router) {
  }

  ngOnInit() {
    this.id=this.act.snapshot.paramMap.get('id')
    this.serviceUser.getUserById(this.id).subscribe(
      res =>{
        console.log(res)
        this.user=res
      },
      err =>{
        console.log(err)
      }
    )
  }

}
