import { Injectable } from '@angular/core';
import { Observable, map } from 'rxjs';

import { User } from '../interface/user';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../environment/environment';

@Injectable({
    providedIn: 'root'
})

export class UserService {
    private apiServerUrl = environment.apiBaseUrl;

    constructor(private http: HttpClient) { }

    public getUsers(): Observable<User[]> {
        return this.http.get<User[]>(`${this.apiServerUrl}/users`);
    }

    public getUsernameByUserId(userId: number): Observable<string> {
        return this.http.get<User>(`${this.apiServerUrl}/users/${userId}`).pipe(
            map(user => user.username)
        );
    }

    public addUser(user: User): Observable<User> {
        return this.http.post<User>(`${this.apiServerUrl}/users/register`, user);
    }

    public loginUser(user: User): Observable<User> {
        return this.http.post<User>(`${this.apiServerUrl}/users/register`, user);
    }

    public updateUser(userId: number, user: User): Observable<User> {
        return this.http.put<User>(`${this.apiServerUrl}/users/${userId}`, user);
    }

    public deleteUser(userId: number): Observable<void> {
        return this.http.delete<void>(`${this.apiServerUrl}/users/${userId}`);
    }
}