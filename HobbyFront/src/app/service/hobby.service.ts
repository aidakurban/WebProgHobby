import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

import { Hobby } from '../interface/hobby';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../environment/environment';

@Injectable({
    providedIn: 'root'
})
export class HobbyService {
    private apiServerUrl = environment.apiBaseUrl;

    constructor(private http: HttpClient) { }

    public getHobbies(): Observable<Hobby[]> {
        return this.http.get<Hobby[]>(`${this.apiServerUrl}/hobbies`);
    }

    public addHobby(hobby: Hobby): Observable<Hobby> {
        return this.http.post<Hobby>(`${this.apiServerUrl}/hobbies`, hobby);
    }

    public updateHobby(hobbyId: number, hobby: Hobby): Observable<Hobby> {
        return this.http.put<Hobby>(`${this.apiServerUrl}/hobbies/${hobbyId}`, hobby);
    }

    public deleteHobby(hobbyId: number): Observable<void> {
        return this.http.delete<void>(`${this.apiServerUrl}/hobbies/${hobbyId}`);
    }

    /*public getHobby(hobbyId: number): Observable<Hobby> {
        return this.http.get<Hobby>(`${this.apiServerUrl}/hobby/${hobbyId}`);
    }*/
}