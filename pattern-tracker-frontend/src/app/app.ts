import { Component, signal } from '@angular/core';
import { PatternUpload } from "./components/pattern-upload/pattern-upload.component";
import { PatternViewerComponent } from "./components/pattern-viewer/pattern-viewer.component";

@Component({
  selector: 'app-root',
  imports: [PatternUpload, PatternViewerComponent],
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class App {
  protected readonly title = signal('pattern-tracker-frontend');
}
