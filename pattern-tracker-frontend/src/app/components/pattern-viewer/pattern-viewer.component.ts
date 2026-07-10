import { CommonModule } from '@angular/common';
import { Component, computed, OnInit, signal } from '@angular/core';
import { PdfViewerModule } from 'ng2-pdf-viewer';
import { PatternService, Pattern } from '../../services/pattern.service';
import { DomSanitizer } from '@angular/platform-browser';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-pattern-viewer',
  standalone: true,
  imports: [CommonModule, PdfViewerModule, RouterLink],
  templateUrl: './pattern-viewer.component.html',
  styleUrl: './pattern-viewer.component.css',
})
export class PatternViewerComponent implements OnInit {
  patternTitle = signal<string>('');
  blobUrl = signal<string | null>(null);

  pdfSrc = computed(() => {
    const url = this.blobUrl();
    return url ? this.sanitizer.bypassSecurityTrustResourceUrl(url) : null;
  });

  constructor(
    private route: ActivatedRoute,
    private http: HttpClient,
    private sanitizer: DomSanitizer
  ) {}

  ngOnInit(): void {
    const patternId = this.route.snapshot.paramMap.get('id');

    if (patternId) {
      this.http.get<any>(`http://localhost:8080/api/patterns/${patternId}`).subscribe(meta => {
        this.patternTitle.set(meta.title);
      });

      this.http.get(`http://localhost:8080/api/patterns/${patternId}/download`, { responseType: 'blob' })
        .subscribe(blob => {
          this.blobUrl.set(URL.createObjectURL(blob));
        });
    }
  }
}
