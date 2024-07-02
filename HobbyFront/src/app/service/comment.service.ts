import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

import { Comment } from '../interface/comment';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../environment/environment';

@Injectable({
    providedIn: 'root'
})

export class CommentService {
    private apiServerUrl = environment.apiBaseUrl;

    constructor(private http: HttpClient) { }

    public getComments(): Observable<Comment[]> {
        return this.http.get<Comment[]>(`${this.apiServerUrl}/comments`);
    }

    public getCommentsByHobbyId(hobbyId: number): Observable<Comment[]> {
        return this.http.get<Comment[]>(`${this.apiServerUrl}/comments/hobby/${hobbyId}`);
    }

    public getCommentsByUserId(userId: number): Observable<Comment[]> {
        return this.http.get<Comment[]>(`${this.apiServerUrl}/comments/user/${userId}`);
    }

    public addComment(comment: Comment): Observable<Comment> {
        return this.http.post<Comment>(`${this.apiServerUrl}/comments`, comment);
    }

    public updateComment(commentId: number, comment: Comment): Observable<Comment> {
        return this.http.put<Comment>(`${this.apiServerUrl}/comments/${commentId}`, comment);
    }

    public deleteComment(commentId: number): Observable<void> {
        return this.http.delete<void>(`${this.apiServerUrl}/comments/${commentId}`);
    }

}