import { Component, Inject, OnInit, PLATFORM_ID } from '@angular/core';

import { isPlatformBrowser } from '@angular/common';

import { ActivatedRoute, Router } from '@angular/router';

import { AuthService } from '../../../core/services/auth-service';
import { Signin } from '../../../core/models/signin.model';
import { response } from 'express';

@Component({
  selector: 'app-callback',
  imports: [],
  templateUrl: './callback.html',
  styleUrl: './callback.css',
})
export class Callback implements OnInit {
  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private authService: AuthService,

    @Inject(PLATFORM_ID)
    private platformId: Object,
  ) {}

  ngOnInit(): void {
    // Prevent SSR execution
    if (!isPlatformBrowser(this.platformId)) {
      return;
    }

    this.route.queryParams.subscribe((params) => {
      const code = params['code'];
      const error = params['error'];

      if (error) {
        alert('Login failed');
        this.router.navigate(['/login']);
        return;
      }

      if (!code) {
        alert('Invalid login response');
        this.router.navigate(['/login']);
        return;
      }

      const data: Signin = {
        code: code,
      };

      this.authService.signIn(data).subscribe({
        next: (response) => {
          console.log(response);

          this.router.navigate(['/dashboard'], {
            state: {
              user: response,
            },
          });
        },
        error: () => {
          console.log(error);

          alert('Authentication failed');
          this.router.navigate(['']);
        },
      });
    });
  }
}
