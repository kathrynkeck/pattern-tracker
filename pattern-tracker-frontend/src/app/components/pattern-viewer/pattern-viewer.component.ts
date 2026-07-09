import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { PdfViewerModule } from 'ng2-pdf-viewer';
import { PatternService, Pattern } from '../../services/pattern.service';

@Component({
  selector: 'app-pattern-viewer',
  standalone: true,
  imports: [CommonModule, PdfViewerModule],
  templateUrl: './pattern-viewer.component.html',
  styleUrl: './pattern-viewer.component.css',
})
export class PatternViewerComponent implements OnInit {
  patternData: Pattern | null = null;
  pdfSrc: string = '';
  isLoading: boolean = true;
  errorMessage: string = '';

  constructor(private patternService: PatternService) {}

  ngOnInit(): void {
    const targetId = 153; //todo: change this to real id

    this.patternService.getPatternById(targetId).subscribe({
      next: (data) => {
        this.patternData = data;
        this.pdfSrc = `http://localhost:8080/api/patterns/${data.id}/download`;
        this.isLoading = false;
      },
      error: (error) => {
        console.error('Failed to load pattern data', error);
        this.errorMessage = 'Failed to load pattern data';
        this.isLoading = false;
      },
    });
  }
}
