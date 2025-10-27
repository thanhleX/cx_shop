import { Component, ViewChild } from '@angular/core';
import { UserService } from '../../services/user.service';
import { Router } from '@angular/router';
import { LoginDto } from '../../dtos/user/login.dto';
import { NgForm } from '@angular/forms';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss'
})
export class LoginComponent {
  @ViewChild('loginForm') loginForm!: NgForm;
  
  phoneNumber: string = '0912345679';
  password: string = '123456';

  constructor(private userService: UserService, private router: Router) { }

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
        next: () => {
          debugger;
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
