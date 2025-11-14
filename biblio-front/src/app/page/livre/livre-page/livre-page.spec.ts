import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LivrePage } from './livre-page';

describe('LivrePage', () => {
  let component: LivrePage;
  let fixture: ComponentFixture<LivrePage>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [LivrePage]
    })
    .compileComponents();

    fixture = TestBed.createComponent(LivrePage);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
