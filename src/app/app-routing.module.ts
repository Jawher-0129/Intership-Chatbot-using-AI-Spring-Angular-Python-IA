import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {RegisterComponent} from "./register/register.component";
import {ListUsersComponent} from "./list-users/list-users.component";
import {UpdateComponent} from "./update/update.component";
import {AjoutComponent} from "./ajout/ajout.component";
import {LoginComponent} from "./login/login.component";

const routes: Routes = [
  {path:'ajout',component:AjoutComponent},
  {path:'list',component:ListUsersComponent},
  {path:'register',component:RegisterComponent},
  {path:'update/:id',component:UpdateComponent},
  {path:'login',component:LoginComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
