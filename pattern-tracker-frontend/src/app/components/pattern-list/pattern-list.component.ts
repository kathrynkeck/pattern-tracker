import { HttpClient } from '@angular/common/http';
import { Component, OnInit, signal } from '@angular/core';
import { RouterLink } from '@angular/router';
import { PatternThumbnailComponent } from '../pattern-thumbnail/pattern-thumbnail.component';
import { DatePipe } from '@angular/common';

export interface PatternSummary {
  id: number;
  title: string;
  uploadedDateTime: string;
  description: string;
  isWip: boolean;
}

@Component({
  selector: 'app-pattern-list',
  imports: [RouterLink, PatternThumbnailComponent, DatePipe],
  templateUrl: './pattern-list.component.html',
  styleUrl: './pattern-list.component.css',
})
export class PatternListComponent implements OnInit{
  patterns = signal<PatternSummary[]>([]);

  constructor(private http: HttpClient) {}
  
  ngOnInit(): void {
    this.http.get<PatternSummary[]>('http://localhost:8080/api/patterns')
      .subscribe(data => this.patterns.set(data));
  }
}
