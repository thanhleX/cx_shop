import { Component, OnInit } from '@angular/core';
import { UserService } from '../../services/user.service';
import { UserResponse } from '../../responses/user/user.response';
import { NgbPopoverConfig } from '@ng-bootstrap/ng-bootstrap';
import { TokenService } from '../../services/token.service';
import { NavigationEnd, Router } from '@angular/router';
import { filter } from 'rxjs';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrl: './header.component.scss'
})
export class HeaderComponent implements OnInit {
  userResponse?: UserResponse | null;
  isPopoverOpen = false;
  activeNavItem: number = 0;

  constructor(
    private userService: UserService,
    private popoverConfig: NgbPopoverConfig,
    private tokenService: TokenService,
    private router: Router
  ) { }

  ngOnInit() {
    // this.userResponse = this.userService.getUserResponseFromLocalStorage();

    // tối ưu ui/ux
    this.userService.user$.subscribe(user => {
      this.userResponse = user;
    });

    this.router.events
      .pipe(filter(event => event instanceof NavigationEnd))
      .subscribe((event: any) => {
        const url = event.urlAfterRedirects;

        if (url === '/') {
          this.activeNavItem = 0;
        } else if (url.startsWith('/notifications')) {
          this.activeNavItem = 1;
        } else if (url.startsWith('/orders')) {
          this.activeNavItem = 2;
        } else if (url.startsWith('/login')) {
          this.activeNavItem = 3;
        } else {
          this.activeNavItem = -1;
        }
      });
  }

  setActiveNavItem(index: number) {
    this.activeNavItem = index;
  }

  togglePopover(event: Event): void {
    event.preventDefault();
    this.isPopoverOpen = !this.isPopoverOpen;
  }

  handleItemClick(index: number): void {
    if (index === 0) {
      this.router.navigate(['/user-profile']);
    }

    if (index === 2) {
      this.userService.removeUserFromLocalStorage();
      this.tokenService.removeToken();
      this.userResponse = this.userService.getUserResponseFromLocalStorage();
      this.router.navigate(['/']);
    }
    this.isPopoverOpen = false;
  }
}
