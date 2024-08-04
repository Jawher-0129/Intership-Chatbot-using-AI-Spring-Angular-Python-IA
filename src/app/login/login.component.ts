import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup,Validators} from "@angular/forms";
import {ServiceUserService} from "../service-user.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit{

  loginForm: FormGroup;


  message: string | null = null;
  messageClass: string | null = null;




  constructor(private service: ServiceUserService,private fb: FormBuilder,private router: Router) {
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
        if(response.jwtToken !=null) {
          console.log("your token is " + response.jwtToken);
          alert("successfully logged in");
          const jwtToken = response.jwtToken;
          localStorage.setItem('jwtToken', jwtToken);
          this.router.navigateByUrl("/home");
        }
      },
      err =>{
        console.log(err);
        alert("erreur login");
      }
    )
  }




}
