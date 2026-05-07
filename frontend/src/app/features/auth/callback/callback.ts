import { Component, OnInit } from '@angular/core';
import { tick } from '@angular/core/testing';
import { ActivatedRoute, Router } from '@angular/router';

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
      }

      console.log(code);
    });
  }
}
