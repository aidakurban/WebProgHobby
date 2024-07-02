import { Component, OnInit } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { NgFor } from '@angular/common';
import { NgIf } from '@angular/common';
import { NgModule } from '@angular/core';
import { NgForm, FormsModule } from '@angular/forms';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, NgFor, FormsModule, NgIf],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  title = "Hobby manager";
}


