import { Component, OnInit } from '@angular/core';
import { RouterLink } from '@angular/router';
import { SigninResponse } from '../../../core/models/signin_response.model';

@Component({
  selector: 'app-dashboard',
  imports: [RouterLink],
  templateUrl: './dashboard.html',
  styleUrl: './dashboard.css',
})
export class Dashboard implements OnInit {
  state: SigninResponse | null = null;

  ngOnInit(): void {
    this.state = history.state.user;
  }
}
