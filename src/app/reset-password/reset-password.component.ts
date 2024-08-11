import { Component, OnInit } from '@angular/core';
import { ActivatedRoute,Router } from '@angular/router';
import { ServiceUserService } from '../service-user.service';

@Component({
  selector: 'app-reset-password',
  templateUrl: './reset-password.component.html',
  styleUrls: ['./reset-password.component.css']
})
export class ResetPasswordComponent implements OnInit {

  password: string = '';
  confirmPassword: string = '';
  token: string = '';

  constructor(private route: ActivatedRoute, private serviceUser: ServiceUserService,private router: Router) {}

  ngOnInit(): void {
    this.route.queryParams.subscribe(params => {
      this.token = params['token'];
    });
  }

  onSubmit() {
    if (this.password === this.confirmPassword) {
      this.serviceUser.resetPassword(this.token, this.password).subscribe(response => {
        alert('Password has been reset.');
        this.router.navigateByUrl("/login")

      }, error => {
        alert('Invalid token or error occurred.');
      });
    } else {
      alert('Passwords do not match.');
    }
  }
}
