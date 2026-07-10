import { Routes } from '@angular/router';
import { PatternUpload } from './components/pattern-upload/pattern-upload.component';
import { PatternViewerComponent } from './components/pattern-viewer/pattern-viewer.component';
import { PatternListComponent } from './components/pattern-list/pattern-list.component';

export const routes: Routes = [
    { path: '', redirectTo: 'patterns', pathMatch: 'full' },
    { path: 'patterns', component: PatternListComponent },
    { path: 'upload', component: PatternUpload },
    { path: 'view/:id', component: PatternViewerComponent },
    { path: '**', redirectTo: 'view' }
];
