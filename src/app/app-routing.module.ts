import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {RegisterComponent} from "./register/register.component";
import {ListUsersComponent} from "./list-users/list-users.component";
import {UpdateComponent} from "./update/update.component";

const routes: Routes = [
  {path:'ajout',component:RegisterComponent},
  {path:'list',component:ListUsersComponent},
  {path:'update/:id',component:UpdateComponent}

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
