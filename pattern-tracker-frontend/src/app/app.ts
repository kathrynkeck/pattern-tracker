import { Component, signal } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { PatternUpload } from "./components/pattern-upload/pattern-upload.component";
import { PatternViewerComponent } from "./components/pattern-viewer/pattern-viewer.component";
import { Header } from "./components/header/header.component";

@Component({
  selector: 'app-root',
  imports: [PatternUpload, PatternViewerComponent, Header, RouterOutlet],
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class App {
  protected readonly title = signal('pattern-tracker-frontend');
}
