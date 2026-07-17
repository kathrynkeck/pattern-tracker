import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PatternThumbnailComponent } from './pattern-thumbnail.component';

describe('PatternThumbnailComponent', () => {
  let component: PatternThumbnailComponent;
  let fixture: ComponentFixture<PatternThumbnailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PatternThumbnailComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(PatternThumbnailComponent);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
