import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ContactFormSliderComponent } from './contact-form-slider.component';

describe('ContactFormSliderComponent', () => {
  let component: ContactFormSliderComponent;
  let fixture: ComponentFixture<ContactFormSliderComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ContactFormSliderComponent]
    });
    fixture = TestBed.createComponent(ContactFormSliderComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
