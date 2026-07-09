import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PatternUpload } from './pattern-upload.component';

describe('PatternUpload', () => {
  let component: PatternUpload;
  let fixture: ComponentFixture<PatternUpload>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PatternUpload],
    }).compileComponents();

    fixture = TestBed.createComponent(PatternUpload);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
