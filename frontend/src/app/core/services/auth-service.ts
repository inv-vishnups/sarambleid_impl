import { Injectable } from '@angular/core';
import { SignupRequest } from '../models/signup-request.model';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../../environments/environment';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private readonly apiUrl = environment.apiUrl;

  constructor(private _http: HttpClient) {}

  signup(data: SignupRequest) {
    return this._http.post(`${this.apiUrl}/auth/register`, data);
  }
}
