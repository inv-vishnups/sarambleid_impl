import { Component } from '@angular/core';
import { RouterLink } from '@angular/router';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { email } from '@angular/forms/signals';
import { AuthService } from '../../../core/services/auth-service';
import { SignupRequest } from '../../../core/models/signup-request.model';

@Component({
  selector: 'app-signup',
  imports: [RouterLink, ReactiveFormsModule],
  templateUrl: './signup.html',
  styleUrl: './signup.css',
})
export class Signup {
  signupForm = new FormGroup({
    firstName: new FormControl('', [
      Validators.required,
      Validators.minLength(3),
      Validators.maxLength(100),
    ]),
    lastName: new FormControl('', [
      Validators.required,
      Validators.minLength(3),
      Validators.maxLength(100),
    ]),
    email: new FormControl('', [Validators.required, Validators.email]),
  });

  constructor(private authService: AuthService) {}

  signup() {
    if (this.signupForm.invalid) {
      this.signupForm.markAllAsTouched();
      return;
    }

    console.log(this.signupForm.value);

    const signupData: SignupRequest = {
      firstName: this.firstName?.value ?? '',
      lastName: this.lastName?.value ?? '',
      email: this.email?.value ?? '',
    };

    this.authService.signup(signupData).subscribe({
      next: (response) => {
        alert('User Created');
      },
      error: (error) => {
        alert('Failed');
      },
    });
  }

  get firstName() {
    return this.signupForm.get('firstName');
  }

  get lastName() {
    return this.signupForm.get('lastName');
  }

  get email() {
    return this.signupForm.get('email');
  }
}
