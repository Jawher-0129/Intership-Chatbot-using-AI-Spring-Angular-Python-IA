import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup,Validators } from "@angular/forms";
import {ServiceUserService} from "../service-user.service";

@Component({
  selector: 'app-ajout',
  templateUrl: './ajout.component.html',
  styleUrls: ['./ajout.component.css']
})
export class AjoutComponent implements OnInit{

  ajoutForm: FormGroup;
  alertMessage: string = '';
  alertType: string = '';


  constructor(private serviceUser: ServiceUserService, private fb: FormBuilder) {
    this.ajoutForm = this.fb.group({
      nom: ['', [Validators.required, Validators.pattern('^[a-zA-Z ]*$')]],
      prenom: ['', [Validators.required, Validators.pattern('^[a-zA-Z ]*$')]],
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(6)]]
    });
  }

  ngOnInit(): void {
  }

  onAjout(): void {
    if (this.ajoutForm.valid) {
      this.serviceUser.addUser(this.ajoutForm.value).subscribe(
        response => {
          console.log('Utilisateur ajouté avec succès', response);
          this.alertMessage = 'Utilisateur ajouté avec succès';
          this.alertType = 'success';

        },
        error => {
          console.error('Erreur lors de l\'ajout de l\'utilisateur', error);
          this.alertMessage = 'Erreur lors de l\'ajout de l\'utilisateur';
          this.alertType = 'danger';
        }
      );
    }
  }


}
