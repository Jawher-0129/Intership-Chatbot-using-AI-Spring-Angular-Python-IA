import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {RegisterComponent} from "./register/register.component";
import {ListUsersComponent} from "./list-users/list-users.component";
import {UpdateComponent} from "./update/update.component";
import {AjoutComponent} from "./ajout/ajout.component";
import {LoginComponent} from "./login/login.component";
import {HomeComponent} from "./home/home.component";
import {ForgotPasswordComponent} from "./forgot-password/forgot-password.component";
import {ResetPasswordComponent} from "./reset-password/reset-password.component";
import {ChatbotComponent} from "./chatbot/chatbot.component";

const routes: Routes = [
  {path:'ajout',component:AjoutComponent},
  {path:'list',component:ListUsersComponent},
  {path:'register',component:RegisterComponent},
  {path:'update/:id',component:UpdateComponent},
  {path:'login',component:LoginComponent},
  {path:'home',component:HomeComponent},
  {path:'forgotpassword',component:ForgotPasswordComponent},
  {path:'resetpassword',component:ResetPasswordComponent},
  {path:'chatbot',component:ChatbotComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
