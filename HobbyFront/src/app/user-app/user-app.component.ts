import { Component, OnInit } from '@angular/core';
import { UserService } from '../service/user.service';
import { User } from '../interface/user';
import { NgFor, NgIf } from '@angular/common';
import { NgForm, FormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { HttpErrorResponse } from '@angular/common/http';
import { tap, catchError } from 'rxjs/operators';
import { Router } from '@angular/router';

@Component({
  selector: 'app-user-app',
  standalone: true,
  imports: [RouterModule, NgFor, FormsModule, NgIf],
  templateUrl: './user-app.component.html',
  styleUrl: './user-app.component.css'
})
export class UserAppComponent implements OnInit {

  public users: User[] | undefined;
  public editUser: User | undefined;
  public deleteUser: User | undefined;
  public loginUser: User | undefined;

  constructor(private userService: UserService, private router: Router) { };
  
  public goToPlannedHobbies(userId: number) {
    this.router.navigate(['/plannedhobbies/user/', userId]);
  }

  showAlert: boolean = false;

  ngOnInit(): void {
    this.getUsers();
    if (this.users?.length === 0) {
      this.showAlert = true;
    }
  }

  public closeAlert(): void {
    this.showAlert = false;
  }

  public getUsers(): void {
    const observer = {
      next: (response: User[]) => this.users = response,
      error: (error: any) => alert(error.message)
    };

    this.userService.getUsers().subscribe(observer);
  }

  public onOpenModal(user: User | undefined, mode: string): void {
    const container = document.getElementById('user-container');
    const button = document.createElement('button');
    button.type = 'button';
    button.style.display = 'none';
    button.setAttribute('data-toggle', 'modal');
    if (mode === 'add') {
      button.setAttribute('data-target', '#addUserModal');
    }
    if (mode === 'login') {
      this.loginUser = user;
      button.setAttribute('data-target', '#loginUserModal');
    }
    if (mode === 'edit') {
      this.editUser = user;
      button.setAttribute('data-target', '#updateUserModal');
    }
    if (mode === 'delete') {
      this.deleteUser = user;
      button.setAttribute('data-target', '#deleteUserModal');
    }
    container?.appendChild(button);
    button.click();
  }

  public onAddUser(addForm: NgForm): void {
    const addUserForm = document.getElementById('add-user-form');
    if (addUserForm) {
      addUserForm.click();
    }

    this.userService.addUser(addForm.value).pipe(
      tap((response: User) => {
        console.log(response);
        this.getUsers();
        addForm.reset();
      }),
      catchError((error: HttpErrorResponse) => {
        alert(error.message);
        addForm.reset();
        throw error;
      })
    ).subscribe();
  }
/*
  public onLoginUser(loginForm: NgForm): void {
    const loginUserForm = document.getElementById('login-user-form');
    if (loginUserForm) {
      loginUserForm.click();
    }

    this.userService.addUser(addForm.value).pipe(
      tap((response: User) => {
        console.log(response);
        this.getUsers();
        addForm.reset();
      }),
      catchError((error: HttpErrorResponse) => {
        alert(error.message);
        addForm.reset();
        throw error;
      })
    ).subscribe();
  }
*/
  public onUpdateUser(id: number | undefined, user: User): void {
    if (id === null || id === undefined) {
      console.error('Invalid User ID or User object');
      return;
    }

    this.userService.updateUser(id, user).pipe(
      tap((response: User) => {
        console.log(response);
        this.getUsers();
      }),
      catchError((error: HttpErrorResponse) => {
        alert(error.message);
        throw error;
      })
    ).subscribe();
  }


  public onDeleteUser(id: number | undefined): void {
    if (id !== undefined && id !== null) {
      this.userService.deleteUser(id).pipe(
        tap(() => {
          console.log('User deleted successfully');
          this.getUsers();
        }),
        catchError((error: HttpErrorResponse) => {
          alert(error.message);
          throw error;
        })
      ).subscribe();
    } else {
      console.error('Invalid User ID');
    }
  }

  public searchUser(key: string): void {
    console.log(key);
    const results: User[] = [];
    if (this.users) { // Проверяем, что this.users определено
      for (const user of this.users) {
        if (user.username.toLowerCase().indexOf(key.toLowerCase()) !== -1 ||
            user.fullName.toLowerCase().indexOf(key.toLowerCase()) !== -1) 
          {
          results.push(user);
        }
      }
    }
    this.users = results;
    if (results.length === 0 || !key) {
      this.getUsers();
    }
  }

}

  