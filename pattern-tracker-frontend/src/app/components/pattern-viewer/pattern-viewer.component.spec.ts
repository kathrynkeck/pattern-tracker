import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PatternViewerComponent } from './pattern-viewer.component';

describe('PatternViewerComponent', () => {
  let component: PatternViewerComponent;
  let fixture: ComponentFixture<PatternViewerComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PatternViewerComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(PatternViewerComponent);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
