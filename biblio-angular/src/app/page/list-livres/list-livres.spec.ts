import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ListLivres } from './list-livres';

describe('ListLivres', () => {
  let component: ListLivres;
  let fixture: ComponentFixture<ListLivres>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ListLivres]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ListLivres);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
