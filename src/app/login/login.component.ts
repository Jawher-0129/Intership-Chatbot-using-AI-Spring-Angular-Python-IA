import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {ServiceUserService} from "../service-user.service";
import {Router} from "@angular/router";
import {User} from "../user.model";
import {SessionService} from "../session.service";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  loginForm: FormGroup;
  siteKey: string = '6LfUciEqAAAAAIrywddi1XP7vzUVMi8jWE3x2IFm';
  errorMessage: string | null = null;
  showMessage: boolean = false;
  userName: string | null = null;

  constructor(private service: ServiceUserService, private fb: FormBuilder, private router: Router, private sessionService: SessionService) {
    this.loginForm = this.fb.group({
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(6)]],
    });
  }

  ngOnInit() {
    const refreshed = sessionStorage.getItem('refreshed');
    if (!refreshed) {
      sessionStorage.setItem('refreshed', 'true');
      location.reload();
    }
    this.initGoogleSignIn();
  }

  OnLogin() {
    this.service.login(this.loginForm.value).subscribe(
      response => {
        if (response.jwtToken != null) {
          console.log("your token is " + response.jwtToken);
          const jwtToken = response.jwtToken;
          localStorage.setItem('jwtToken', jwtToken);
          const user: User = {
            id: response.id,
            nom: response.nom,
            prenom: response.prenom,
            email: response.email,
            role: response.role,
            active: response.active
          };
          this.sessionService.setUser(user);
          localStorage.setItem('user', JSON.stringify(user));
          this.userName = response.nom;
          this.showMessage = true;
          alert("Bienvenue "+response.nom)
          this.router.navigateByUrl("/home").then(() => {
            window.location.reload();
          });
          
        }
      },
      err => {
        console.log(err);
        this.errorMessage = 'Email ou mot de passe incorrect.';
      }
    )
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
        console.log("Google login successful:", response);
      },
      err => {
        console.log("Google login error:", err);
      }
    );
  }
}
