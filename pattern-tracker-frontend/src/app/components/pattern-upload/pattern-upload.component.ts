import { Component } from '@angular/core';
import { PatternService } from '../../services/pattern.service';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-pattern-upload',
  imports: [CommonModule, FormsModule],
  templateUrl: './pattern-upload.component.html',
  styleUrl: './pattern-upload.component.css',
})
export class PatternUpload {
  patternTitle: string = '';
  selectedFile: File | null = null;

  constructor(private patternService: PatternService) {}

  onFileSelected(event: any): void {
    const file: File = event.target.files[0];
    if (file) {
      this.selectedFile = file;
    }
  }

  onUpload(): void {
    if (!this.patternTitle || !this.selectedFile) {
      alert('Please enter a title and select a PDF file first!');
      return;
    }

    this.patternService.uploadPattern(this.patternTitle, this.selectedFile).subscribe({
      next: (response) => {
        console.log('Upload successful!', response);
        alert('Pattern uploaded successfully!');
        // Reset form
        this.patternTitle = '';
        this.selectedFile = null;
      },
      error: (error) => {
        console.error('Upload failed', error);
        alert('Failed to upload pattern.');
      }
    });
  }
}
