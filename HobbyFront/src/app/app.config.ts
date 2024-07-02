import { ApplicationConfig } from '@angular/core';
import { provideRouter } from '@angular/router';

import { routes } from './app.routes';

import { provideHttpClient } from '@angular/common/http';
import { HobbyAppComponent } from './hobby-app/hobby-app.component';
import { CommentAppComponent } from './comment-app/comment-app.component';
import { UserAppComponent } from './user-app/user-app.component';

export const appConfig: ApplicationConfig = {
  providers: [provideRouter(routes), provideHttpClient(), 
              HobbyAppComponent, CommentAppComponent,
              UserAppComponent]
};
