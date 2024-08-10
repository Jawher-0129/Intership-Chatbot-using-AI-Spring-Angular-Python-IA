import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { ServiceUserService } from '../service-user.service';
import { SessionService } from '../session.service';
import { User } from '../user.model';
import { ChartData, ChartOptions } from 'chart.js';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
  message: string = '';
  user: User | null = null;

  public chartData: ChartData<'pie'> = {
    labels: [],
    datasets: [{
      data: [],
      backgroundColor: ['#FF6384', '#36A2EB', '#FFCE56'],
    }]
  };

  public chartOptions: ChartOptions<'pie'> = {
    responsive: true,
  };

  constructor(
    private serviceUser: ServiceUserService,
    private sessionService: SessionService,
    private cdr: ChangeDetectorRef
  ) { }

  hello() {
    this.serviceUser.hello().subscribe(
      response => {
        console.log(response);
        this.message = response.message;
      },
      error => {
        console.log(error);
      }
    );
  }

  loadUserStats() {
    this.serviceUser.getTopActiveUsers().subscribe(users => {
      const totalActive = users.reduce((sum, user) => sum + user.active, 0);
      this.chartData.labels = users.map(user => `${user.nom} ${user.prenom}`);
      this.chartData.datasets[0].data = users.map(user => (user.active / totalActive) * 100);

      // Utiliser setTimeout pour permettre à Angular de stabiliser le DOM
      setTimeout(() => {
        this.cdr.detectChanges();
      }, 0);
    }, error => {
      console.error('Error fetching user data:', error);
    });
  }

  ngOnInit() {
    this.hello();
    this.user = this.sessionService.getUser();
    this.loadUserStats();
  }
}
