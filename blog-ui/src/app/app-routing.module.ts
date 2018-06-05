import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { CommonModule } from '@angular/common';
import { MyPostsComponent } from './my-posts/my-posts.component';

import { LoginComponent } from './login/login.component';
const appRoutes: Routes = [
  { path: '', component: LoginComponent },
  { path: 'my-posts', component: MyPostsComponent }
];
@NgModule({
  imports: [
    CommonModule,
    RouterModule.forRoot(appRoutes, { enableTracing: true })
  ],
  declarations: []
})
export class AppRoutingModule { }
