import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TouristattactionsComponent } from './touristattactions.component';

describe('TouristattactionsComponent', () => {
  let component: TouristattactionsComponent;
  let fixture: ComponentFixture<TouristattactionsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ TouristattactionsComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(TouristattactionsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
