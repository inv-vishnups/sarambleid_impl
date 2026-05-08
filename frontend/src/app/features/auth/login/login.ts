import { Component } from '@angular/core';
import { RouterLink } from '@angular/router';
import { environment } from '../../../../environments/environment';

@Component({
  selector: 'app-login',
  imports: [RouterLink],
  templateUrl: './login.html',
  styleUrl: './login.css',
})
export class Login {
  private readonly baseUrl = environment.scrambleidBaseUrl;
  private readonly client_id = environment.clientId;
  private readonly redirect_uri = environment.redirectUri;
  private readonly scope = environment.scope;

  loginWithScramble() {
    const url =
      this.baseUrl + '&client_id=' + this.client_id + '&scope='+ this.scope + '&redirect_uri=' + this.redirect_uri ;

    window.location.href = url;
  }
}
