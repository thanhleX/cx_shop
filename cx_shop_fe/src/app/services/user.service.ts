import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { RegisterDto } from '../dtos/user/register.dto';
import { LoginDto } from '../dtos/user/login.dto';
import { environment } from '../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private apiRegister = environment.apiBaseUrl + '/users/register';
  private apiLogin = environment.apiBaseUrl + '/users/login';

  private apiConfig = {
    headers: this.createHeader(),
  }

  constructor(private http: HttpClient) { }

  private createHeader(): HttpHeaders {
    return new HttpHeaders({ 'Content-Type': 'application/json' });
  }

  register(registerDto: RegisterDto): Observable<any> {
    return this.http.post(this.apiRegister, registerDto, this.apiConfig);
  }

  login(loginDto: LoginDto): Observable<any> {
    // tra ve token dang json
    // return this.http.post(this.apiLogin, loginDto, this.apiConfig);

    // tra ve token dang string
    return this.http.post(this.apiLogin, loginDto, {
      headers: this.createHeader(),
      responseType: 'text'
    });
  }
}
