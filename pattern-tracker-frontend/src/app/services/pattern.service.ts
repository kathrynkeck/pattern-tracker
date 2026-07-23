import { Service } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

export interface Pattern {
  id: number;
  title: string;
  fileStoragePath: string;
  counters: any[]; // We can strongly type this later!
}

@Injectable({ providedIn: 'root' })
export class PatternService {
  private apiUrl = 'http://localhost:8080/api/patterns';

  constructor(private http: HttpClient) {}

  uploadPattern(title: string, file: File, description: string): Observable<any> {
    const formData = new FormData();
    formData.append('title', title);
    formData.append('file', file);
    formData.append('description', description);

    return this.http.post(`${this.apiUrl}/upload`, formData);
  }

  getPatternById(id: number): Observable<Pattern> {
    return this.http.get<Pattern>(`${this.apiUrl}/${id}`);
  }
}
