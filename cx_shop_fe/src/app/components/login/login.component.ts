import { Component, OnInit, ViewChild } from '@angular/core';
import { UserService } from '../../services/user.service';
import { Router } from '@angular/router';
import { LoginDto } from '../../dtos/user/login.dto';
import { NgForm } from '@angular/forms';
import { LoginResponse } from '../../responses/user/login.response';
import { TokenService } from '../../services/token.service';
import { RoleService } from '../../services/role.service';
import { Role } from '../../models/role';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss'
})
export class LoginComponent implements OnInit {
  @ViewChild('loginForm') loginForm!: NgForm;

  phoneNumber: string = '0912345679';
  password: string = '123456';

  roles: Role[] = [];
  rememberMe: boolean = true;
  selectedRole: Role | undefined;

  constructor(
    private router: Router,
    private userService: UserService,
    private tokenService: TokenService,
    private roleService: RoleService
  ) { }

  ngOnInit() {
    debugger;
    this.roleService.getRoles().subscribe({
      next: (roles: Role[]) => {
        debugger;
        this.roles = roles;
        this.selectedRole = roles.length > 0 ? roles[0] : undefined;
      },
      error: (err: any) => {
        debugger;
        console.error('Error getting roles: ', err);
      }
    });
  }

  onPhoneNumberChange(): void {
    console.log(`Phone typed: ${this.phoneNumber}`);
  }

  login() {
    const message = 'phone: ' + this.phoneNumber + "\dpassword: " + this.password;

    debugger;

    const loginDto: LoginDto = {
      "phone_number": this.phoneNumber,
      "password": this.password,
      "role_id": this.selectedRole?.id ?? 1
    };

    this.userService.login(loginDto)
      .subscribe({
        next: (response: LoginResponse) => {
          debugger;
          const { token } = response;
          if (this.rememberMe) {
            this.tokenService.setToken(token);
          }
          // this.router.navigate(['/']);
        },
        complete: () => {
          debugger;
        },
        error: (error: any) => {
          debugger;
          alert(error?.error?.message)
        }
      });
  }

  createAccount() {
    
  }
}
