import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { PlannedHobby } from '../interface/plannedhobby';
import { PlannedHobbyService } from '../service/plannedhobby.service';

import { NgForm, FormsModule } from '@angular/forms';
import { tap, catchError, map } from 'rxjs/operators';
import { HttpErrorResponse } from '@angular/common/http';

import { NgFor, NgIf } from '@angular/common';
import { Router } from '@angular/router';
import { RouterModule } from '@angular/router';
//import { UserService } from '../service/user.service';
//import { User } from '../interface/user';

@Component({
  selector: 'app-planned-hobbies',
  standalone: true,
  imports: [NgFor, NgIf, FormsModule, RouterModule],
  templateUrl: './planned-hobbies.component.html',
  styleUrl: './planned-hobbies.component.css'
})

export class PlannedHobbiesComponent implements OnInit {

  //public hobbyId: number | undefined;
  public userId: number | undefined;
  public plannedHobbies: PlannedHobby[] | undefined;

  public editPlannedHobby: PlannedHobby | undefined;
  public deletePlannedHobby: PlannedHobby | undefined;

  //public currentDateTime: string | undefined;
  //public currentDateTime: string = "";

  //public username: string | undefined;

  //1 public loggedIn: boolean;
  //1 public currentRoute: string;

  constructor(private route: ActivatedRoute, private plannedHobbyService: PlannedHobbyService,
    private router: Router) { //, private userService: UserService) { 
      //1 this.currentRoute = this.router.url;
    }

  showAlert: boolean = false;

  ngOnInit(): void {
    this.route.params.subscribe(params => {

      this.userId = +params['userId'];
      if (this.userId !== undefined) {
        this.getPlannedHobbiesFiltered();
      }

      if (this.plannedHobbies?.length === 0) {
        this.showAlert = true;
      }

      /*if (this.comments) { // Проверяем, что this.comments определено
        for (const comment of this.comments) {
            this.getUsernameByUserId(comment.userId);
        }
      }*/
    });
  }

  public closeAlert(): void {
    this.showAlert = false;
  }

  public getAllPlannedHobbies(): void {
    const observer = {
      next: (response: PlannedHobby[]) => this.plannedHobbies = response,
      error: (error: any) => alert(error.message)
    };

    this.plannedHobbyService.getPlannedHobbies().subscribe(observer);
  }


  public getPlannedHobbiesFiltered(): void {
    if (this.userId !== undefined) {
      this.plannedHobbyService.getPlannedHobbiesByUserId(this.userId).subscribe(plannedHobbies => {
        this.plannedHobbies = plannedHobbies;
      });
    } 
  }
  /*public getUsernameByUserId(userId: number): void {
    this.userService.getUsernameByUserId(userId).pipe(
      map(username => {
        this.username = username;
        console.log('Username:', this.username);
      })
    ).subscribe();
  }*/
  /*
  public getUsernameByUserId(userId: number): void {
    this.userService.getUsernameByUserId(userId).subscribe(
      username => {
        this.username = username;
        console.log('Username:', this.username);
      }
    );
  }*/

  public searchPlannedHobby(key: string): void {
    console.log(key);
    const results: PlannedHobby[] = [];
    if (this.plannedHobbies) { // Проверяем, что определено
      for (const plannedHobby of this.plannedHobbies) {

        if (plannedHobby.plannedHobbyDateTime.toLowerCase().indexOf(key.toLowerCase()) !== -1 
        ) {
          results.push(plannedHobby);
        }
      }
    }
    this.plannedHobbies = results;
    if (results.length === 0 || !key) {
      this.getAllPlannedHobbies();
    }
  }

  public onOpenModal(plannedHobby: PlannedHobby | undefined, mode: string): void {
    const container = document.getElementById('plannedHobby-container');
    const button = document.createElement('button');
    button.type = 'button';
    button.style.display = 'none';
    button.setAttribute('data-toggle', 'modal');
    if (mode === 'add') {
      button.setAttribute('data-target', '#addPlannedHobbyModal');
    }
    if (mode === 'edit') {
      this.editPlannedHobby = plannedHobby;
      button.setAttribute('data-target', '#updatePlannedHobbyModal');
    }
    if (mode === 'delete') {
      this.deletePlannedHobby = plannedHobby;
      button.setAttribute('data-target', '#deletePlannedHobbyModal');
    }
    container?.appendChild(button);
    button.click();
  }

  public onAddPlannedHobby(addForm: NgForm): void {
    const addPlannedHobbyForm = document.getElementById('add-PlannedHobby-form');
    if (addPlannedHobbyForm) {
      addPlannedHobbyForm.click();
    }
      
    this.plannedHobbyService.addPlannedHobby(addForm.value).pipe(
      tap((response: PlannedHobby) => {
        console.log(response);
        this.getAllPlannedHobbies();
        addForm.reset();
      }),
      catchError((error: HttpErrorResponse) => {
        alert(error.message);
        addForm.reset();
        throw error;
      })
    ).subscribe();
  }

  public onUpdatePlannedHobby(id: number | undefined, plannedHobby: PlannedHobby): void {
    if (id === null || id === undefined) {
      console.error('Invalid Planned Hobby ID or object');
      return;
    }

    this.plannedHobbyService.updatePlannedHobby(id, plannedHobby).pipe(
      tap((response: PlannedHobby) => {
        console.log(response);
        this.getAllPlannedHobbies();
      }),
      catchError((error: HttpErrorResponse) => {
        alert(error.message);
        throw error;
      })
    ).subscribe();
  }


  public onDeletePlannedHobby(id: number | undefined): void {
    if (id !== undefined && id !== null) {
      this.plannedHobbyService.deletePlannedHobby(id).pipe(
        tap(() => {
          console.log('Planned hobby deleted successfully');
          this.getAllPlannedHobbies();
        }),
        catchError((error: HttpErrorResponse) => {
          alert(error.message);
          throw error;
        })
      ).subscribe();
    } else {
      console.error('Invalid Planned hobby ID');
    }
  }


}