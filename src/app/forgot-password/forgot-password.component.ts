import { Component } from '@angular/core';
import {ServiceUserService} from "../service-user.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-forgot-password',
  templateUrl: './forgot-password.component.html',
  styleUrls: ['./forgot-password.component.css']
})
export class ForgotPasswordComponent {
  email: string='';


  constructor(private serviceUser: ServiceUserService,private router:Router) {}

  onSubmit() {
    this.serviceUser.forgotPassword(this.email).subscribe(response => {
      alert('Reset link sent to your email.');
      console.log(response)
     // this.router.navigateByUrl("/resetpassword")
    }, error => {
      alert('Email not found.');
      console.log(error)
    });
  }

}
