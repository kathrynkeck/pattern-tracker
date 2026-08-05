import { Component, ElementRef, OnInit, signal, ViewChild } from '@angular/core';
import { PdfViewerModule } from 'ng2-pdf-viewer';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { HttpClient, HttpParams } from '@angular/common/http';
import { DatePipe } from '@angular/common';

@Component({
  selector: 'app-pattern-viewer',
  standalone: true,
  imports: [PdfViewerModule, RouterLink, DatePipe],
  templateUrl: './pattern-viewer.component.html',
  styleUrl: './pattern-viewer.component.css',
})
export class PatternViewerComponent implements OnInit {
  patternTitle = signal<string>('');
  patternDescription = signal<string>('');
  patternUploadDate = signal<string>('');
  patternIsWip = signal<boolean>(false);
  patternIsCompleted = signal<boolean>(false);
  startedDate = signal<string | null>(null);
  completedDate = signal<string | null>(null);
  pdfData = signal<Uint8Array | null>(null);
  isEditingDescription = signal(false);
  tempDescription = signal('');

  @ViewChild('descInput') descInput?: ElementRef<HTMLInputElement>;

  constructor(
    private route: ActivatedRoute,
    private http: HttpClient
  ) { }

  toggleStatus(): void {
    const patternId = this.route.snapshot.paramMap.get('id');
    if (!patternId) return;

    if (this.patternIsCompleted()) {
      alert('This pattern is already completed and cannot be moved back to Work in Progress.');
      return;
    }

    if (this.patternIsWip()) {
      this.http.patch<any>(`http://localhost:8080/api/patterns/${patternId}/completed/true`, null)
        .subscribe({
          next: (updatedPattern) => {
            this.patternIsCompleted.set(true);
            this.patternIsWip.set(false);

            if (updatedPattern.completedDate) {
              this.completedDate.set(updatedPattern.completedDate);
            }
          },
          error: (err) => {
            console.error('Failed to update status:', err);
            alert('Failed to update pattern status. Please try again.');
          }
        });
    } else {
      this.http.patch<any>(`http://localhost:8080/api/patterns/${patternId}/wip/true`, null)
        .subscribe({
          next: (updatedPattern) => {
            this.patternIsWip.set(true);

            if (updatedPattern.startedDate) {
              this.startedDate.set(updatedPattern.startedDate);
            }
          },
          error: (err) => {
            console.error('Failed to update status:', err);
            alert('Failed to update pattern status. Please try again.');
          }
        });
    }
  }

  ngOnInit(): void {
    const patternId = this.route.snapshot.paramMap.get('id');

    if (patternId) {
      this.http.get<any>(`http://localhost:8080/api/patterns/${patternId}`).subscribe(meta => {
        this.patternTitle.set(meta.title);
        this.patternDescription.set(meta.description);
        this.patternUploadDate.set(meta.uploadedDateTime);
        this.patternIsWip.set(meta.isWip);
        this.patternIsCompleted.set(meta.isCompleted ?? meta.completed ?? false);
        this.startedDate.set(meta.startedDate ?? null);
        this.completedDate.set(meta.completedDate ?? null);
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

  enableEditDescription(): void {
    this.tempDescription.set(this.patternDescription());
    this.isEditingDescription.set(true);
    setTimeout(() => this.descInput?.nativeElement.focus(), 0);
  }

  onDescriptionInput(event: Event): void {
    const value = (event.target as HTMLTextAreaElement).value;
    this.tempDescription.set(value);
  }

  handleKeyDown(event: Event): void {
    const kbEvent = event as KeyboardEvent;
    if (kbEvent.key === 'Enter' && (kbEvent.ctrlKey || kbEvent.metaKey)) {
      kbEvent.preventDefault();
      this.saveDescription();
    }
  }

  saveDescription(): void {
    const updated = this.tempDescription().trim();
    const patternId = this.route.snapshot.paramMap.get('id');

    if (!updated || !patternId) {
      this.isEditingDescription.set(false);
      return;
    }

    // Set up query parameters to match Spring's @RequestParam("description")
    const params = new HttpParams().set('description', updated);

    // Send request (body is null/empty since data is sent via params)
    this.http.patch<any>(`http://localhost:8080/api/patterns/${patternId}/description`, null, { params })
      .subscribe({
        next: (updatedPattern) => {
          this.patternDescription.set(updatedPattern.description ?? updated);
          this.isEditingDescription.set(false);
        },
        error: (err) => {
          console.error('Failed to update description on server:', err);
          alert('Could not update description. Please try again.');
          // Leave isEditingDescription as true so user doesn't lose their input on failure
        }
      });
  }

  cancelEditDescription(): void {
    this.isEditingDescription.set(false)
  }
}
