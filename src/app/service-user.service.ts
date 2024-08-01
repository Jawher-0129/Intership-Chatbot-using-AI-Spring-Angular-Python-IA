import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class ServiceUserService {

  url='http://localhost:8080';

  constructor(private http : HttpClient) { }


  getAllUsers()
  {
    return this.http.get(this.url+"/api/v1/users")
  }

  deleteUser(id : any)
  {
    return this.http.delete(this.url+"/api/v1/deleteuser/"+id);
  }

  signUp(user : any): Observable<any> {
    return this.http.post(this.url+"/api/v1/adduser",user);
  }

  getUserById(id:any)
  {
    return this.http.get(this.url+"/api/v1/users/"+id)
  }

  updateUser(id:any,user:any)
  {
    return this.http.put(this.url+"/api/v1/user/"+id,user)
  }


}
