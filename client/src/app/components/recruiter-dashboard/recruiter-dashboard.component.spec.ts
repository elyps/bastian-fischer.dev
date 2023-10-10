import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RecruiterDashboardComponent } from './recruiter-dashboard.component';

describe('RecruiterDashboardComponent', () => {
  let component: RecruiterDashboardComponent;
  let fixture: ComponentFixture<RecruiterDashboardComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [RecruiterDashboardComponent]
    });
    fixture = TestBed.createComponent(RecruiterDashboardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
