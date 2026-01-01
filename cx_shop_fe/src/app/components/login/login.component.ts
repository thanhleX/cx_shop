import { Component, OnInit, ViewChild } from '@angular/core';
import { UserService } from '../../services/user.service';
import { Router } from '@angular/router';
import { LoginDto } from '../../dtos/user/login.dto';
import { NgForm } from '@angular/forms';
import { LoginResponse } from '../../responses/user/login.response';
import { TokenService } from '../../services/token.service';
import { RoleService } from '../../services/role.service';
import { Role } from '../../models/role';
import { UserResponse } from '../../responses/user/user.response';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss'
})
export class LoginComponent implements OnInit {
  @ViewChild('loginForm') loginForm!: NgForm;

  /*
  // login user
  phoneNumber: string = '0912345676';
  password: string = '123456';
  */

  // login admin
  phoneNumber: string = '0101010101';
  password: string = 'admin123';

  roles: Role[] = [];
  rememberMe: boolean = true;
  selectedRole: Role | undefined;
  userResponse?: UserResponse;

  constructor(
    private router: Router,
    private userService: UserService,
    private tokenService: TokenService,
    private roleService: RoleService,
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

    this.userService.login(loginDto).subscribe({
      next: (response: LoginResponse) => {
        debugger;
        const { token } = response;
        if (this.rememberMe) {
          this.tokenService.setToken(token);
          debugger;
          this.userService.getUserDetail(token).subscribe({
            next: (response: any) => {
              debugger;
              this.userResponse = {
                ...response,
                date_of_birth: new Date(response.date_of_birth),
              };
              this.userService.saveUserResponseToLocalStorage(this.userResponse);
              if (this.userResponse?.role.name == 'ADMIN') {
                this.router.navigate(['/admin']);
              } else if (this.userResponse?.role.name == 'USER') {
                this.router.navigate(['/']);
              }
            },
            complete: () => {
              debugger;
            },
            error: (error: any) => {
              debugger;
              alert(error.error.message);
            }
          });
        }
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
    this.router.navigate(['/register']);
  }
}
