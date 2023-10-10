import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CustomerPortalComponent } from './customer-portal.component';

describe('CustomerPortalComponent', () => {
  let component: CustomerPortalComponent;
  let fixture: ComponentFixture<CustomerPortalComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [CustomerPortalComponent]
    });
    fixture = TestBed.createComponent(CustomerPortalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
