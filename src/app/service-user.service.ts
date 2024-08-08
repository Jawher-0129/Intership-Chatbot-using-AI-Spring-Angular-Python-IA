import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
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
    return this.http.post(this.url+"/signup/p1",user);
  }

  addUser(user : any): Observable<any> {
    return this.http.post(this.url+"/api/v1/adduser", user);
  }

  getUserById(id:any)
  {
    return this.http.get(this.url+"/api/v1/users/"+id)
  }

  updateUser(id:any,user:any)
  {
    return this.http.put(this.url+"/api/v1/user/"+id,user)
  }
  login(signRequest : any): Observable<any> {
    return this.http.post(this.url+"/login/p2",signRequest);
  }





  hello() : Observable<any> {
    return this.http.get(this.url+"/api/hello",{
      headers: this.createAuthorizationHeader()
    });
  }

  private createAuthorizationHeader(): HttpHeaders {
    const jwtToken = localStorage.getItem('jwtToken');
    if (jwtToken) {
      console.log("JWT token found in local storage", jwtToken);
      return new HttpHeaders().set("Authorization", "Bearer " + jwtToken);
    } else {
      console.log("JWT token not found in local storage");
      return new HttpHeaders();
    }
  }

  loginWithGoogle(idToken: string): Observable<any> {
    return this.http.post(this.url + "/login/info", { idToken });
  }

  forgotPassword(email: string): Observable<any> {
    return this.http.post(`${this.url}/api/auth/forgot-password`, { email });
  }

  resetPassword(token: string, newPassword: string): Observable<any> {
    return this.http.post(`${this.url}/api/auth/reset-password`, { token, newPassword });
  }


  getChatbotResponse(message: string): Observable<any> {
    return this.http.post<any>(this.url+"/api/v1/chatbot",{ message });
  }

  downloadPdf(): Observable<Blob> {
    return this.http.get(`${this.url}/api/v1/download-users-pdf`, { responseType: 'blob' });
  }



  logout(): void {
    localStorage.removeItem('jwtToken');
    localStorage.removeItem('user');
  }








}
