import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ServiceUserService } from '../service-user.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {
  registerForm: FormGroup;

  constructor(private fb: FormBuilder, private serviceuser: ServiceUserService) {
    this.registerForm = this.fb.group({
      nom: ['', [Validators.required, Validators.pattern('^[a-zA-ZÀ-ÿ\\s\\-\'"]*$')]],
      prenom: ['', [Validators.required, Validators.pattern('^[a-zA-ZÀ-ÿ\\s\\-\'"]*$')]],
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(6)]]
    });
  }

  ngOnInit(): void {}

  onSubmit() {
    if (this.registerForm.valid) {
      this.serviceuser.signUp(this.registerForm.value)
        .subscribe(
          res => {
            console.log("User created successfully", res);
          },
          err => {
            console.log(err);
          }
        );
    }
  }
}
