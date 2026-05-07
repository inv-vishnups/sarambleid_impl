import { Component, OnInit } from '@angular/core';
import { tick } from '@angular/core/testing';
import { ActivatedRoute, Router } from '@angular/router';
import { AuthService } from '../../../core/services/auth-service';
import { Signin } from '../../../core/models/signin.model';

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
  ) {}

  ngOnInit(): void {
    this.route.queryParams.subscribe((params) => {
      const code = params['code'];
      const error = params['error'];

      if (error) {
        console.log('LOGIN ERROR', error);
        alert('Login failed');
        this.router.navigate(['/login']);
      }

      if (!code) {
        console.log('NO CODE FOUND');
        alert('Invalid login response');
        this.router.navigate(['/login']);
        return;
      }

      const data: Signin = {
        code: code,
      };

      this.authService.signIn(data).subscribe({
        next: (response) => {
          this.router.navigate(['/dashboard'], {
            state: {
              user: response,
            },
          });
        },
        error: (error) => {
          alert(error);
        },
      });

      console.log(code);
    });
  }
}
