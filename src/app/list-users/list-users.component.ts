import { Component, OnInit } from '@angular/core';
import { ServiceUserService } from '../service-user.service';

@Component({
  selector: 'app-list-users',
  templateUrl: './list-users.component.html',
  styleUrls: ['./list-users.component.css']
})
export class ListUsersComponent implements OnInit {
  users: any[] = [];  
  filteredUsers: any[] = []; 
  searchTerm: string = '';

  constructor(private serviceUser: ServiceUserService) {}

  ngOnInit() {
    this.afficherUsers();
  }

  afficherUsers() {
    this.serviceUser.getAllUsers().subscribe(
      (res: any[]) => {
        console.log(res);
        this.users = res;
        this.filteredUsers = this.users;
      },
      err => {
        console.log(err);
      }
    );
  }

  filterUsers() {
    if (!this.searchTerm) {
      this.filteredUsers = this.users;
    } else {
      this.filteredUsers = this.users.filter(user =>
        user.nom.toLowerCase().includes(this.searchTerm.toLowerCase()) ||
        user.prenom.toLowerCase().includes(this.searchTerm.toLowerCase()) ||
        user.email.toLowerCase().includes(this.searchTerm.toLowerCase())
      );
    }
  }

  SupprimerUser(id: any, index: number) {
    this.serviceUser.deleteUser(id).subscribe(
      res => {
        console.log(res);
        this.users.splice(index, 1);
      },
      err => {
        console.log(err);
      }
    );
  }

  downloadPdf() {
    this.serviceUser.downloadPdf().subscribe((response: Blob) => {
      const link = document.createElement('a');
      link.href = window.URL.createObjectURL(response);
      link.download = 'users.pdf';
      link.click();
    }, error => {
      console.error('Error downloading the PDF', error);
    });
  }
}
