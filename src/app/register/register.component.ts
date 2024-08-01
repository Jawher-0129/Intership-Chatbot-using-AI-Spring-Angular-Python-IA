import {Component, OnInit} from '@angular/core';
import {ServiceUserService} from "../service-user.service";

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit{

  user = {
    email: '',
    nom: '',
    prenom: '',
    password: '',
    role: '' // Si vous avez un champ rôle dans votre DTO
  };

  onSubmit()
  {
    this.serviceuser.signUp(this.user)
      .subscribe(
        res => {
          console.log("User created successfully", res)
        },
        err => {
          console.log(err)
        }
      );

  }

  ngOnInit(): void {

  }

  constructor(private serviceuser :ServiceUserService) {

  }


}
