import { Component, OnInit } from '@angular/core';
import { AbstractControl, FormBuilder, FormGroup, ValidationErrors, ValidatorFn, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { UserService } from '../../services/user.service';
import { TokenService } from '../../services/token.service';
import { UserResponse } from '../../responses/user/user.response';
import { UpdateUserDto } from '../../dtos/user/update.user.dto';

@Component({
  selector: 'app-user-profile',
  templateUrl: './user-profile.component.html',
  styleUrl: './user-profile.component.scss'
})
export class UserProfileComponent implements OnInit {
  userProfileForm: FormGroup;
  userResponse?: UserResponse;
  token: string = '';

  constructor(
    private formBuilder: FormBuilder,
    private router: Router,
    private activatedRoute: ActivatedRoute,
    private userService: UserService,
    private tokenService: TokenService,

  ) {
    this.userProfileForm = this.formBuilder.group({
      full_name: [''],
      address: ['', Validators.minLength(5)],
      password: ['', Validators.minLength(6)],
      retype_password: ['', Validators.minLength(6)],
      date_of_birth: ['']
    }, {
      validators: this.passwordMatchValidator
    });
  }

  ngOnInit(): void {
    debugger;
    let token: string = this.tokenService.getToken() ?? '';
    this.userService.getUserDetail(token).subscribe({
      next: (response: any) => {
        debugger;
        this.userResponse = {
          ...response,
          date_of_birth: new Date(response.date_of_birth),
        };
        this.userProfileForm.patchValue({
          full_name: this.userResponse?.full_name ?? '',
          address: this.userResponse?.address ?? '',
          date_of_birth: this.userResponse?.date_of_birth.toISOString().substring(0, 10),
        });
        this.userService.saveUserResponseToLocalStorage(this.userResponse);
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

  passwordMatchValidator(): ValidatorFn {
    return (formGroup: AbstractControl): ValidationErrors | null => {
      const password = formGroup.get('password')?.value;
      const retypedPassword = formGroup.get('retype_password')?.value;
      if (password !== retypedPassword) {
        return { passwordMismatch: true };
      }
      return null;
    };
  }

  save(): void {
    debugger;
    if (this.userProfileForm.valid) {
      const updateUserDto: UpdateUserDto = {
        full_name: this.userProfileForm.get('full_name')?.value,
        address: this.userProfileForm.get('address')?.value,
        password: this.userProfileForm.get('password')?.value,
        retype_password: this.userProfileForm.get('retype_password')?.value,
        date_of_birth: this.userProfileForm.get('date_of_birth')?.value
      };

      this.userService.updateUserDetail(this.token, updateUserDto).subscribe({
        next: (resposne: any) => {
          debugger;
          this.userService.removeUserFromLocalStorage();
          this.tokenService.removeToken();
          this.router.navigate(['/login']);
        },
        error: (error: any) => {
          debugger;
          alert(error.error.message);
        }
      });
    } else {
      if (this.userProfileForm.hasError('passwordMistmatch')) {
        alert('Mat khau khong khop');
      }
    }
  }
}
