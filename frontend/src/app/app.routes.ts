import { Routes } from '@angular/router';
import path from 'path';

export const routes: Routes = [
  {
    path: '',
    redirectTo: 'login',
    pathMatch: 'full',
  },

  {
    path: 'login',
    loadComponent: () => import('./features/auth/login/login').then((m) => m.Login),
  },

  {
    path: 'signup',
    loadComponent: () => import('./features/auth/signup/signup').then((m) => m.Signup),
  },
  {
    path: 'dashboard',
    loadComponent: () =>
      import('./features/dashboard/dashboard/dashboard').then((m) => m.Dashboard),
  },
  {
    path: 'callback',
    loadComponent: () => import('./features/auth/callback/callback').then((m) => m.Callback),
  },
  {
    path: 'success',
    loadComponent: () =>
      import('./features/auth/signin-success/signin-success').then((m) => m.SigninSuccess),
  },
  {
    path: 'failed',
    loadComponent: () =>
      import('./features/auth/signin-failed/signin-failed').then((m) => m.SigninFailed),
  },
];
