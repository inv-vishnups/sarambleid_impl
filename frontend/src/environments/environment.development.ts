export const environment = {
  production: false,

  apiUrl: 'http://localhost:8080/api',

  // ? ScrambleID uri, token
  scrambleidBaseUrl: 'https://prod.scrambleid.com/oidc/inno/authorize?response_type=code',
  clientId: '9962fe04-d484-45e5-935d-e9fb83573cde',
  scope: 'openid',
  redirectUri: 'http://localhost:4200/callback',
};
