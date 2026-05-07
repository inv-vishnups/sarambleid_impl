import { Injectable } from '@angular/core';
import { SignupRequest } from '../models/signup-request.model';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../../environments/environment';
import { Signin } from '../models/signin.model';
import { SigninResponse } from '../models/signin_response.model';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private readonly apiUrl = environment.apiUrl;

  constructor(private _http: HttpClient) {}

  signup(data: SignupRequest) {
    return this._http.post(`${this.apiUrl}/auth/register`, data, {
      responseType: 'text',
    });
  }

  signIn(data: Signin) {
    return this._http.post<SigninResponse>(`${this.apiUrl}/auth/login`, data);
  }
}
