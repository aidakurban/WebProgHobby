import { Component, OnInit } from '@angular/core';
import { HobbyService } from '../service/hobby.service';
import { Hobby } from '../interface/hobby';
import { NgFor, NgIf } from '@angular/common';
import { NgForm, FormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { HttpErrorResponse } from '@angular/common/http';
import { tap, catchError } from 'rxjs/operators';
import { Router } from '@angular/router';

@Component({
  selector: 'app-hobby-app',
  standalone: true,
  imports: [RouterModule, NgFor, FormsModule, NgIf],
  templateUrl: './hobby-app.component.html',
  styleUrl: './hobby-app.component.css'
})
export class HobbyAppComponent implements OnInit {
  public hobbies: Hobby[] | undefined;
  public editHobby: Hobby | undefined;
  public deleteHobby: Hobby | undefined;

  constructor(private hobbyService: HobbyService, private router: Router) { };
  
  public goToMaterials(hobbyId: number) {
    this.router.navigate(['/materials/hobby/', hobbyId]);
  }
  
  public goToComments(hobbyId: number) {
    this.router.navigate(['/comments/hobby/', hobbyId]);
  }

  showAlert: boolean = false;

  ngOnInit(): void {
    this.getHobbies();
    if (this.hobbies?.length === 0) {
      this.showAlert = true;
    }
  }
  public closeAlert(): void {
    this.showAlert = false;
  }

  public getHobbies(): void {
    const observer = {
      next: (response: Hobby[]) => this.hobbies = response,
      error: (error: any) => alert(error.message)
    };

    this.hobbyService.getHobbies().subscribe(observer);
  }

  public onOpenModal(hobby: Hobby | undefined, mode: string): void {
    const container = document.getElementById('hobby-container');
    const button = document.createElement('button');
    button.type = 'button';
    button.style.display = 'none';
    button.setAttribute('data-toggle', 'modal');
    if (mode === 'add') {
      button.setAttribute('data-target', '#addHobbyModal');
    }
    if (mode === 'edit') {
      this.editHobby = hobby;
      button.setAttribute('data-target', '#updateHobbyModal');
    }
    if (mode === 'delete') {
      this.deleteHobby = hobby;
      button.setAttribute('data-target', '#deleteHobbyModal');
    }
    container?.appendChild(button);
    button.click();
  }

  public onAddHobby(addForm: NgForm): void {
    const addHobbyForm = document.getElementById('add-hobby-form');
    if (addHobbyForm) {
      addHobbyForm.click();
    }

    this.hobbyService.addHobby(addForm.value).pipe(
      tap((response: Hobby) => {
        console.log(response);
        this.getHobbies();
        addForm.reset();
      }),
      catchError((error: HttpErrorResponse) => {
        alert(error.message);
        addForm.reset();
        throw error;
      })
    ).subscribe();
  }

  public onUpdateHobby(id: number | undefined, hobby: Hobby): void {
    if (id === null || id === undefined) {
      console.error('Invalid hobby ID or hobby object');
      return;
    }

    this.hobbyService.updateHobby(id, hobby).pipe(
      tap((response: Hobby) => {
        console.log(response);
        this.getHobbies();
      }),
      catchError((error: HttpErrorResponse) => {
        alert(error.message);
        throw error;
      })
    ).subscribe();
  }


  public onDeleteHobby(id: number | undefined): void {
    if (id !== undefined && id !== null) {
      this.hobbyService.deleteHobby(id).pipe(
        tap(() => {
          console.log('Hobby deleted successfully');
          this.getHobbies();
        }),
        catchError((error: HttpErrorResponse) => {
          alert(error.message);
          throw error;
        })
      ).subscribe();
    } else {
      console.error('Invalid hobby ID');
    }
  }

  public searchHobby(key: string): void {
    console.log(key);
    const results: Hobby[] = [];
    if (this.hobbies) { // Проверяем, что this.hobbies определено
      for (const hobby of this.hobbies) {
        if (hobby.name.toLowerCase().indexOf(key.toLowerCase()) !== -1 ||
          hobby.category.toLowerCase().indexOf(key.toLowerCase()) !== -1 ||
          hobby.description.toLowerCase().indexOf(key.toLowerCase()) !== -1) {
          results.push(hobby);
        }
      }
    }
    this.hobbies = results;
    if (results.length === 0 || !key) {
      this.getHobbies();
    }
  }



}
