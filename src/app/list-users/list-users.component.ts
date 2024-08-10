import {Component, OnInit} from '@angular/core';
import {ServiceUserService} from "../service-user.service";

@Component({
  selector: 'app-list-users',
  templateUrl: './list-users.component.html',
  styleUrls: ['./list-users.component.css']
})
export class ListUsersComponent implements OnInit{


  users: any=[]
  filteredUsers: any[] = [];
  searchTerm: string = '';
  isPopupVisible: boolean = false;

  


  constructor(private serviceUser: ServiceUserService) {
  }

  afficherUsers()
  {
    this.serviceUser.getAllUsers().subscribe(
      res =>{
        console.log(res)
        this.users=res
        
      },
      err =>{
        console.log(err)
      }
    );
  }
  
  SupprimerUser(id:any,index :number)
  {
    this.serviceUser.deleteUser(id).subscribe(
      res =>{
        console.log(res)
        this.users.split(index,1)
      },
      err =>{
        console.log(err)
      }
    )
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

  

  ngOnInit() {
   this.afficherUsers()
  }

}
