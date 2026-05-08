export const environment = {
  production: true,

  apiUrl: 'https://api.myapp.com',

  // ? ScrambleID url, token, details
  scrambleidBaseUrl: 'https://prod.scrambleid.com/oidc/inno/authorize?response_type=code',
  clientId: 'b1105227-9fe8-4f90-89d7-fb0eb79c0621',
  scope: 'openid',
  redirectUri: 'http://localhost:4200/callback',
};
