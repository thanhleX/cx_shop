import { Component, ViewChild } from '@angular/core';
import { UserService } from '../../services/user.service';
import { Router } from '@angular/router';
import { LoginDto } from '../../dtos/user/login.dto';
import { NgForm } from '@angular/forms';
import { LoginResponse } from '../../responses/user/login.response';
import { TokenService } from '../../services/token.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss'
})
export class LoginComponent {
  @ViewChild('loginForm') loginForm!: NgForm;

  phoneNumber: string = '0912345679';
  password: string = '123456';

  constructor(
    private router: Router,
    private userService: UserService,
    private tokenService: TokenService
  ) { }

  onPhoneNumberChange(): void {
    console.log(`Phone typed: ${this.phoneNumber}`);
  }

  login() {
    const message = 'phone: ' + this.phoneNumber + "\dpassword: " + this.password;

    debugger;

    const loginDto: LoginDto = {
      "phone_number": this.phoneNumber,
      "password": this.password
    };

    this.userService.login(loginDto)
      .subscribe({
        next: (response: LoginResponse) => {
          debugger;
          const { token } = response;
          this.tokenService.setToken(token);
          // this.router.navigate(['/']);
        },
        complete: () => {
          debugger;
        },
        error: (error: any) => {
          alert(`Cannot register, error: ${error.error}`)
          console.error(error);
        }
      });
  }
}
