import { Component, Input, OnInit, ElementRef, ViewChild } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import * as pdfjsLib from 'pdfjs-dist';

pdfjsLib.GlobalWorkerOptions.workerSrc = './assets/pdf.worker.min.mjs';

@Component({
  selector: 'app-pattern-thumbnail',
  standalone: true,
  templateUrl: './pattern-thumbnail.component.html',
  styleUrl: './pattern-thumbnail.component.css'
})
export class PatternThumbnailComponent implements OnInit {
  @Input() patternId!: number;
  @ViewChild('thumbnailCanvas', { static: true }) canvasRef!: ElementRef<HTMLCanvasElement>;

  constructor(private http: HttpClient) {}

  ngOnInit(): void {
    // Fetch the binary file stream for this specific pattern card
    this.http.get(`http://localhost:8080/api/patterns/${this.patternId}/download`, { responseType: 'blob' })
      .subscribe({
        next: async (blob) => {
          const arrayBuffer = await blob.arrayBuffer();
          
          // Load the document matrix using pdf.js core engine
          const loadingTask = pdfjsLib.getDocument({ data: arrayBuffer });
          const pdf = await loadingTask.promise;
          
          // Grab only the first page
          const page = await pdf.getPage(1);
          
          const canvas = this.canvasRef.nativeElement;
          const context = canvas.getContext('2d');
          
          // Configure a low-resolution scale optimal for grid thumbnails
          const viewport = page.getViewport({ scale: 0.5 });
          canvas.height = viewport.height;
          canvas.width = viewport.width;

          if (context) {
            await page.render({ canvasContext: context, viewport: viewport }).promise;
          }
          
          // Instantly clean up file references from browser document memory
          pdf.destroy();
        },
        error: (err) => console.error('Could not render thumbnail preview', err)
      });
  }
}