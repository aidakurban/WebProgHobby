import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Material } from '../interface/material';
import { MaterialService} from '../service/material.service';

import { NgForm, FormsModule } from '@angular/forms';
import { tap, catchError, map } from 'rxjs/operators';
import { HttpErrorResponse } from '@angular/common/http';

import { NgFor, NgIf } from '@angular/common';
import { Router } from '@angular/router';
import { RouterModule } from '@angular/router';
//import { UserService } from '../service/user.service';
//import { User } from '../interface/user';

@Component({
  selector: 'app-materials-app',
  standalone: true,
  imports: [NgFor, NgIf, FormsModule, RouterModule],
  templateUrl: './materials-app.component.html',
  styleUrls: ['./materials-app.component.css']
})

export class MaterialsAppComponent implements OnInit {

  public hobbyId: number | undefined;
  public materials: Material[] | undefined;

  public editMaterial: Material | undefined;
  public deleteMaterial: Material | undefined;

  //public currentDateTime: string = "";

  //public username: string | undefined;

  constructor(private route: ActivatedRoute, private materialService: MaterialService,
    private router: Router) { //, private userService: UserService) { 
    }

  showAlert: boolean = false;

  ngOnInit(): void {
    this.route.params.subscribe(params => {

      this.hobbyId = +params['hobbyId'];
      if (this.hobbyId !== undefined) {
        this.getMaterialsFiltered();
      }

      if (this.materials?.length === 0) {
        this.showAlert = true;
      }

    });
  }

  public closeAlert(): void {
    this.showAlert = false;
  }

  public getAllMaterials(): void {
    const observer = {
      next: (response: Material[]) => this.materials = response,
      error: (error: any) => alert(error.message)
    };

    this.materialService.getMaterials().subscribe(observer);
  }


  public getMaterialsFiltered(): void {

    if (this.hobbyId !== undefined) {
      this.materialService.getMaterialsByHobbyId(this.hobbyId).subscribe(materials => {
        this.materials = materials;
      });
    }

  }
  public searchMaterial(key: string): void {
    console.log(key);
    const results: Material[] = [];
    if (this.materials) { // Проверяем, что определено
      for (const material of this.materials) {
        if (material.materialName.toLowerCase().indexOf(key.toLowerCase()) !== -1 
          //|| this.username?.length !== 0
          //|| this.username?.toLowerCase().indexOf(key.toLowerCase()) !== -1
        ) {
          results.push(material);
        }
      }
    }
    this.materials = results;
    if (results.length === 0 || !key) {
      this.getAllMaterials();
    }
  }

  public onOpenModal(material: Material | undefined, mode: string): void {
    const container = document.getElementById('material-container');
    const button = document.createElement('button');
    button.type = 'button';
    button.style.display = 'none';
    button.setAttribute('data-toggle', 'modal');
    if (mode === 'add') {
      button.setAttribute('data-target', '#addMaterialModal');
    }
    if (mode === 'edit') {
      this.editMaterial = material;
      button.setAttribute('data-target', '#updateMaterialModal');
    }
    if (mode === 'delete') {
      this.deleteMaterial = material;
      button.setAttribute('data-target', '#deleteMaterialModal');
    }
    container?.appendChild(button);
    button.click();
  }

  public onAddMaterial(addForm: NgForm): void {
    const addMaterialForm = document.getElementById('add-material-form');
    if (addMaterialForm) {
      addMaterialForm.click();
    }
      
    this.materialService.addMaterial(addForm.value).pipe(
      tap((response: Material) => {
        console.log(response);
        this.getAllMaterials();
        addForm.reset();
      }),
      catchError((error: HttpErrorResponse) => {
        alert(error.message);
        addForm.reset();
        throw error;
      })
    ).subscribe();
  }

  public onUpdateMaterial(id: number | undefined, material: Material): void {
    if (id === null || id === undefined) {
      console.error('Invalid Material ID or Material object');
      return;
    }

    this.materialService.updateMaterial(id, material).pipe(
      tap((response: Material) => {
        console.log(response);
        this.getAllMaterials();
      }),
      catchError((error: HttpErrorResponse) => {
        alert(error.message);
        throw error;
      })
    ).subscribe();
  }


  public onDeleteMaterial(id: number | undefined): void {
    if (id !== undefined && id !== null) {
      this.materialService.deleteMaterial(id).pipe(
        tap(() => {
          console.log('Material deleted successfully');
          this.getAllMaterials();
        }),
        catchError((error: HttpErrorResponse) => {
          alert(error.message);
          throw error;
        })
      ).subscribe();
    } else {
      console.error('Invalid Material ID');
    }
  }


}