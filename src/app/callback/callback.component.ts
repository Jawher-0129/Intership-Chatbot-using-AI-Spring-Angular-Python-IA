import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { ActivatedRoute,Router } from '@angular/router';

@Component({
  selector: 'app-callback',
  templateUrl: './callback.component.html',
  styleUrls: ['./callback.component.css']
})
export class CallbackComponent implements OnInit {

  constructor(private route: ActivatedRoute, private http: HttpClient,private router:Router) {}

  ngOnInit() {
    const code = this.route.snapshot.queryParamMap.get('code');
    if (code) {
      console.log('GitHub code:', code);
      this.http.get(`http://localhost:8080/login/github?code=${code}`).subscribe(
        response => {
          console.log('User authenticated successfully', response);
        },
        error => {
          console.error('Error during GitHub authentication', error);
        }
      );
    } else {
      console.error('No code found in query params');
    }
  }
}
