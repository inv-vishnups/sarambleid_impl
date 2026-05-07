export const environment = {
  production: false,

  apiUrl: 'http://localhost:8080/api',

  tokenKey: 'access_token',

  // ? ScrambleID uri, token
  scrambleidBaseUrl: 'https://prod.scrambleid.com/oidc/inno/authorize?response_type=code',
  clientId: '01485052-e818-443d-bc3a-0c993f94d89d',
  redirectUri: 'http://localhost:4200/callback',
};
