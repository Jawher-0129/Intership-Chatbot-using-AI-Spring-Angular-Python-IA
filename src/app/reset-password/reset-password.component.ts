import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {ServiceUserService} from "../service-user.service";

@Component({
  selector: 'app-reset-password',
  templateUrl: './reset-password.component.html',
  styleUrls: ['./reset-password.component.css']
})
export class ResetPasswordComponent implements OnInit{

  password: string='';
  token: string='';

  constructor(private route: ActivatedRoute, private serviceUser: ServiceUserService) {}

  ngOnInit(): void {
    this.route.queryParams.subscribe(params => {
      this.token = params['token'];
    });
    alert(this.token)
  }

  onSubmit() {
    this.serviceUser.resetPassword(this.token, this.password).subscribe(response => {
      alert('Password has been reset.');
    }, error => {
      alert('Invalid token.');
    });
  }

}
