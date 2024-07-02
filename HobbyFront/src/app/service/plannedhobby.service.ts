import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

import { PlannedHobby } from '../interface/plannedhobby';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../environment/environment';

@Injectable({
    providedIn: 'root'
})

export class PlannedHobbyService {
    private apiServerUrl = environment.apiBaseUrl;

    constructor(private http: HttpClient) { }

    public getPlannedHobbies(): Observable<PlannedHobby[]> {
        return this.http.get<PlannedHobby[]>(`${this.apiServerUrl}/plannedhobbies`);
    }

    public getPlannedHobbiesByUserId(userId: number): Observable<PlannedHobby[]> {
        return this.http.get<PlannedHobby[]>(`${this.apiServerUrl}/plannedhobbies/user/${userId}`);
    }

    public addPlannedHobby(plannedHobby: PlannedHobby): Observable<PlannedHobby> {
        return this.http.post<PlannedHobby>(`${this.apiServerUrl}/plannedhobbies`, plannedHobby);
    }

    public updatePlannedHobby(id: number, plannedHobby: PlannedHobby): Observable<PlannedHobby> {
        return this.http.put<PlannedHobby>(`${this.apiServerUrl}/plannedhobbies/${id}`, plannedHobby);
    }

    public deletePlannedHobby(id: number): Observable<void> {
        return this.http.delete<void>(`${this.apiServerUrl}/plannedhobbies/${id}`);
    }

}