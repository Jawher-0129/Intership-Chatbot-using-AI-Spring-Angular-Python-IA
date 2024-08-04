import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup,Validators} from "@angular/forms";
import {ServiceUserService} from "../service-user.service";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit{

  loginForm: FormGroup;

  message: string | null = null;
  messageClass: string | null = null;




  constructor(private service: ServiceUserService,private fb: FormBuilder) {
    this.loginForm = this.fb.group({
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(6)]]
    });

  }


  ngOnInit() {
  }

  OnLogin()
  {
    this.service.login(this.loginForm.value).subscribe(
      response => {
          console.log("your token is "+response.jwtToken);
          alert("successfully logged in");

      },
      err =>{
        console.log(err);
        alert("erreur login");
      }
    )
  }




}
