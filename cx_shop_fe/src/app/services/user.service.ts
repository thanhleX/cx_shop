import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { RegisterDto } from '../dtos/user/register.dto';
import { LoginDto } from '../dtos/user/login.dto';
import { environment } from '../environments/environment';
import { HttpUtilService } from './http.util.service';
import { UserResponse } from '../responses/user/user.response';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private apiRegister = `${environment.apiBaseUrl}/users/register`;
  private apiLogin = `${environment.apiBaseUrl}/users/login`;
  private apiUserDetail = `${environment.apiBaseUrl}/users/details`;

  private apiConfig = {
    headers: this.createHeader(),
  }

  constructor(private http: HttpClient, private httpUtilService: HttpUtilService) { }

  private createHeader(): HttpHeaders {
    return new HttpHeaders({
      'Content-Type': 'application/json',
      'Accept-Language': 'vi'
    });
  }

  register(registerDto: RegisterDto): Observable<any> {
    return this.http.post(this.apiRegister, registerDto, this.apiConfig);
  }

  login(loginDto: LoginDto): Observable<any> {
    return this.http.post(this.apiLogin, loginDto, this.apiConfig);
  }

  getUserDetail(token: string) {
    return this.http.post(this.apiUserDetail, {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        Authorization: `Bearer ${token}`
      })
    });
  }

  saveUserResponseToLocalStorage(userResponse?: UserResponse) {
    try {
      debugger;
      if (userResponse == null || !userResponse) {
        return;
      }

      const userResponseJSON = JSON.stringify(userResponse);
      localStorage.setItem('user', userResponseJSON);
      console.log('User response saved to local storage');
    } catch (error) {
      console.error('Error saving user response to local storage: ', error);
    }
  }

  getUserResponseFromLocalStorage(): UserResponse | null {
    try {
      const userResponseJSON = localStorage.getItem('user');
      if (userResponseJSON == null || userResponseJSON == undefined) {
        return null;
      }

      const userResponse = JSON.parse(userResponseJSON!);
      console.log('User response retrieved from local storage');
      return userResponse;
    } catch (error) {
      console.error('Error retrieving user response from local storage: ', error);
      return null;
    }
  }

  removeUserFromLocalStorage():void {
    try {
      localStorage.removeItem('user');
      console.log('User data removed from local storage.');
    } catch(error) {
      console.error('Error removed user data from local storage: ', error);
    }
  }
}
