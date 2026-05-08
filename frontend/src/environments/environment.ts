export const environment = {
  production: true,

  apiUrl: 'https://api.myapp.com',

  // ? ScrambleID url, token, details
  scrambleidBaseUrl: 'https://prod.scrambleid.com/oidc/inno/authorize?response_type=code',
  clientId: '9962fe04-d484-45e5-935d-e9fb83573cde',
  scope: 'openid',
  redirectUri: 'http://localhost:4200/callback',
};
