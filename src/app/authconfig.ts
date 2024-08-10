import { AuthConfig } from 'angular-oauth2-oidc';

export const authConfig: AuthConfig = {
  issuer: 'https://github.com/login/oauth/authorize',
  redirectUri: window.location.origin + '/callback',
  clientId: 'Ov23lihM5V47f1ehdypf',
  scope: 'user:email',
  responseType: 'code',
  showDebugInformation: true,
};