import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Comment } from '../interface/comment';
import { CommentService } from '../service/comment.service';

import { NgForm, FormsModule } from '@angular/forms';
import { tap, catchError, map } from 'rxjs/operators';
import { HttpErrorResponse } from '@angular/common/http';

import { NgFor, NgIf } from '@angular/common';
import { Router } from '@angular/router';
import { RouterModule } from '@angular/router';
import { UserService } from '../service/user.service';
import { User } from '../interface/user';

@Component({
  selector: 'app-comment-app',
  standalone: true,
  imports: [NgFor, NgIf, FormsModule, RouterModule],
  templateUrl: './comment-app.component.html',
  styleUrls: ['./comment-app.component.css']
})

export class CommentAppComponent implements OnInit {

  public hobbyId: number | undefined;
  //public userId: number | undefined;
  public comments: Comment[] | undefined;

  public editComment: Comment | undefined;
  public deleteComment: Comment | undefined;

  //public currentDateTime: string | undefined;
  public currentDateTime: string = "";

  public username: string | undefined;

  //1 public loggedIn: boolean;
  //1 public currentRoute: string;

  constructor(private route: ActivatedRoute, private commentService: CommentService,
    private router: Router, private userService: UserService) { 
      //1 this.currentRoute = this.router.url;
    }

  showAlert: boolean = false;

  ngOnInit(): void {
    this.route.params.subscribe(params => {

      this.hobbyId = +params['hobbyId'];
      if (this.hobbyId !== undefined) {
        this.getCommentsFiltered();
      }

      /*
      this.userId = +params['userId'];
      if (this.userId !== undefined) {
        this.getAllComments();
        this.getCommentsFiltered
      }*/

      if (this.comments?.length === 0) {
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

  public getAllComments(): void {
    const observer = {
      next: (response: Comment[]) => this.comments = response,
      error: (error: any) => alert(error.message)
    };

    this.commentService.getComments().subscribe(observer);
  }


  public getCommentsFiltered(): void {

    if (this.hobbyId !== undefined) {
      this.commentService.getCommentsByHobbyId(this.hobbyId).subscribe(comments => {
        this.comments = comments;
      });
    }
    /*
    if (this.userId !== undefined) {
      this.commentService.getCommentsByUserId(this.userId).subscribe(comments => {
        this.comments = comments;
      });
    } */

  }



  /*public getUsernameByUserId(userId: number): void {
    this.userService.getUsernameByUserId(userId).pipe(
      map(username => {
        this.username = username;
        console.log('Username:', this.username);
      })
    ).subscribe();
  }*/
  public getUsernameByUserId(userId: number): void {
    this.userService.getUsernameByUserId(userId).subscribe(
      username => {
        this.username = username;
        console.log('Username:', this.username);
      }
    );
  }

  public searchComment(key: string): void {
    console.log(key);
    const results: Comment[] = [];
    if (this.comments) { // Проверяем, что this.comments определено
      for (const comment of this.comments) {

        //this.getUsernameByUserId(comment.userId);

        if (comment.commentText.toLowerCase().indexOf(key.toLowerCase()) !== -1 
            || comment.commentDateTime.indexOf(key) !== -1 //||
          //|| this.username?.length !== 0
          //|| this.username?.toLowerCase().indexOf(key.toLowerCase()) !== -1
        ) {
          results.push(comment);
        }
      }
    }
    this.comments = results;
    if (results.length === 0 || !key) {
      this.getAllComments();
    }
  }

  public onOpenModal(comment: Comment | undefined, mode: string): void {
    const container = document.getElementById('comment-container');
    const button = document.createElement('button');
    button.type = 'button';
    button.style.display = 'none';
    button.setAttribute('data-toggle', 'modal');
    if (mode === 'add') {
      //this.addComment = comment;
      button.setAttribute('data-target', '#addCommentModal');
    }
    if (mode === 'edit') {
      this.editComment = comment;
      button.setAttribute('data-target', '#updateCommentModal');
    }
    if (mode === 'delete') {
      this.deleteComment = comment;
      button.setAttribute('data-target', '#deleteCommentModal');
    }
    container?.appendChild(button);
    button.click();
  }

  public onAddComment(addForm: NgForm): void {
    const addCommentForm = document.getElementById('add-comment-form');
    if (addCommentForm) {
      addCommentForm.click();
    }
      
    this.commentService.addComment(addForm.value).pipe(
      tap((response: Comment) => {
        console.log(response);
        this.getAllComments();
        addForm.reset();
      }),
      catchError((error: HttpErrorResponse) => {
        alert(error.message);
        addForm.reset();
        throw error;
      })
    ).subscribe();
  }

  public onUpdateComment(id: number | undefined, comment: Comment): void {
    if (id === null || id === undefined) {
      console.error('Invalid Comment ID or Comment object');
      return;
    }

    this.commentService.updateComment(id, comment).pipe(
      tap((response: Comment) => {
        console.log(response);
        this.getAllComments();
      }),
      catchError((error: HttpErrorResponse) => {
        alert(error.message);
        throw error;
      })
    ).subscribe();
  }


  public onDeleteComment(id: number | undefined): void {
    if (id !== undefined && id !== null) {
      this.commentService.deleteComment(id).pipe(
        tap(() => {
          console.log('Comment deleted successfully');
          this.getAllComments();
        }),
        catchError((error: HttpErrorResponse) => {
          alert(error.message);
          throw error;
        })
      ).subscribe();
    } else {
      console.error('Invalid Comment ID');
    }
  }


}