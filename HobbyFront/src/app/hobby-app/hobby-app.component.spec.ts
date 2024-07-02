import { ComponentFixture, TestBed } from '@angular/core/testing';

import { HobbyAppComponent } from './hobby-app.component';

describe('HobbyAppComponent', () => {
  let component: HobbyAppComponent;
  let fixture: ComponentFixture<HobbyAppComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [HobbyAppComponent]
    })
      .compileComponents();

    fixture = TestBed.createComponent(HobbyAppComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
