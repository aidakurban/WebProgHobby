import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

import { Material } from '../interface/material';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../environment/environment';

@Injectable({
    providedIn: 'root'
})

export class MaterialService {
    private apiServerUrl = environment.apiBaseUrl;

    constructor(private http: HttpClient) { }

    public getMaterials(): Observable<Material[]> {
        return this.http.get<Material[]>(`${this.apiServerUrl}/materials`);
    }

    public getMaterialsByHobbyId(hobbyId: number): Observable<Material[]> {
        return this.http.get<Material[]>(`${this.apiServerUrl}/materials/hobby/${hobbyId}`);
    }

    public addMaterial(material: Material): Observable<Material> {
        return this.http.post<Material>(`${this.apiServerUrl}/materials`, material);
    }

    public updateMaterial(id: number, material: Material): Observable<Material> {
        return this.http.put<Material>(`${this.apiServerUrl}/materials/${id}`, material);
    }

    public deleteMaterial(id: number): Observable<void> {
        return this.http.delete<void>(`${this.apiServerUrl}/materials/${id}`);
    }

}