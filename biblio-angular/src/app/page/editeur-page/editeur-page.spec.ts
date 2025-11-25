import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EditeurPage } from './editeur-page';

describe('EditeurPage', () => {
  let component: EditeurPage;
  let fixture: ComponentFixture<EditeurPage>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [EditeurPage]
    })
    .compileComponents();

    fixture = TestBed.createComponent(EditeurPage);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
