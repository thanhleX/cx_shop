import { Component } from '@angular/core';
import { AppRoutingModule } from "./app-routing.module";
import { Router } from '@angular/router';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss'
})
export class AppComponent {
  title = 'cx_shop_fe';

  constructor(public router: Router) {}

  isAdmin(): boolean {
    return this.router.url.startsWith('/admin');
  }
}
