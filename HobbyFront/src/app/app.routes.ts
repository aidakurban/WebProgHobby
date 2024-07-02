import { Routes } from '@angular/router';
import { HobbyAppComponent } from './hobby-app/hobby-app.component';
import { UserAppComponent } from './user-app/user-app.component';
import { CommentAppComponent } from './comment-app/comment-app.component';
import { PlannedHobbiesComponent } from './planned-hobbies/planned-hobbies.component';
import { MaterialsAppComponent } from './materials-app/materials-app.component';

export const routes: Routes = [
    { path: 'hobbies', component: HobbyAppComponent }, 
    { path: 'users', component: UserAppComponent},
    { path: 'comments', component: CommentAppComponent},
    { path: 'comments/hobby/:hobbyId', component: CommentAppComponent }, // Путь для комментариев с параметром hobbyId
    { path: 'plannedhobbies/user/:userId', component: PlannedHobbiesComponent },
    { path: 'materials/hobby/:hobbyId', component: MaterialsAppComponent }
    //, { path: 'comments/user/:userId/hobby/:hobbyId', component: CommentAppComponent }

];
