import { Component, OnInit, signal } from '@angular/core';
import { PdfViewerModule } from 'ng2-pdf-viewer';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-pattern-viewer',
  standalone: true,
  imports: [PdfViewerModule, RouterLink],
  templateUrl: './pattern-viewer.component.html',
  styleUrl: './pattern-viewer.component.css',
})
export class PatternViewerComponent implements OnInit {
  patternTitle = signal<string>('');

  pdfData = signal<Uint8Array | null>(null);

  constructor(
    private route: ActivatedRoute,
    private http: HttpClient
  ) {}

  ngOnInit(): void {
    const patternId = this.route.snapshot.paramMap.get('id');

    if (patternId) {
      this.http.get<any>(`http://localhost:8080/api/patterns/${patternId}`).subscribe(meta => {
        this.patternTitle.set(meta.title);
      });

      this.http.get(`http://localhost:8080/api/patterns/${patternId}/download`, { responseType: 'arraybuffer' })
        .subscribe({
          next: (buffer: ArrayBuffer) => {
            this.pdfData.set(new Uint8Array(buffer));
          },
          error: (err) => {
            console.error('Error fetching file binary data:', err);
          }
        });
    }
  }
}
