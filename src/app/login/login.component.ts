import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup,Validators} from "@angular/forms";
import {ServiceUserService} from "../service-user.service";
import {Router} from "@angular/router";
import {User} from "../user.model";
import {SessionService} from "../session.service";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit{

  loginForm: FormGroup;

  message: string | null = null;
  messageClass: string | null = null;
  errorMessage: string | null = null;



  constructor(private service: ServiceUserService,private fb: FormBuilder,private router: Router,private sessionService: SessionService) {
    this.loginForm = this.fb.group({
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(6)]]
    });

  }

  private initGoogleSignIn() {
    (window as any).google.accounts.id.initialize({
      client_id: '585831849215-jokbgmu013ti2dqr3i30c3126ol3e6at.apps.googleusercontent.com',
      callback: this.handleCredentialResponse
    });
    (window as any).google.accounts.id.prompt();
  }

  private handleCredentialResponse(response: any) {
    const idToken = response.credential;
    this.service.loginWithGoogle(idToken).subscribe(
      response => {
        // Handle successful login
        console.log("Google login successful:", response);
        // Redirect or handle as needed
      },
      err => {
        console.log("Google login error:", err);
        // Handle error
      }
    );
  }


  ngOnInit() {
    this.initGoogleSignIn();


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
          const user: User = {
            id: response.id,
            nom: response.nom,
            prenom: response.prenom,
            email: response.email,
            role: response.role
          };
          this.sessionService.setUser(user);
          localStorage.setItem('user', JSON.stringify(user));
          this.router.navigateByUrl("/home");
        }
      },
      err =>{
        console.log(err);
        this.errorMessage = 'Email ou mot de passe incorrect.';
      }
    )
  }




}
