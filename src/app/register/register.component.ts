import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ServiceUserService } from '../service-user.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {
  registerForm: FormGroup;
  errorMessage: string | null = null;

  
  constructor(private fb: FormBuilder, private serviceuser: ServiceUserService, private router: Router) {
    this.registerForm = this.fb.group({
      nom: ['', [Validators.required, Validators.pattern('^[a-zA-ZÀ-ÿ\\s\\-\'"]*$')]],
      prenom: ['', [Validators.required, Validators.pattern('^[a-zA-ZÀ-ÿ\\s\\-\'"]*$')]],
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(6)]]
    });
  }

  signInWithGitHub()
  {
    const clientID='Ov23lihM5V47f1ehdypf';
    const redirectUri='http://localhost:4200/callback'
    window.location.href = `https://github.com/login/oauth/authorize?client_id=${clientID}&redirect_uri=${redirectUri}`;
    this.router.navigateByUrl("/login")
  }





  ngOnInit(): void {}

  onSubmit() {
    if (this.registerForm.valid) {
      this.serviceuser.signUp(this.registerForm.value)
        .subscribe(
          res => {
            console.log("User created successfully", res);
            this.router.navigateByUrl("/login");
          },
          err => {
            console.log(err);
              this.errorMessage = "Email existe deja";
          }
        );
    }
  }
}
