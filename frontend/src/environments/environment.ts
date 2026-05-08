export const environment = {
  production: true,

  apiUrl: 'https://api.myapp.com',

  // ? ScrambleID url, token, details
  scrambleidBaseUrl: 'https://prod.scrambleid.com/oidc/inno/authorize?response_type=code',
  clientId: 'e500698f-2202-4151-bc1c-d5ac784c6a71',
  scope: 'openid',
  redirectUri: 'http://localhost:4200/callback',
};
