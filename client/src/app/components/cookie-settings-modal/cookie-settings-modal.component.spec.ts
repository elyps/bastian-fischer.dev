import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CookieSettingsModalComponent } from './cookie-settings-modal.component';

describe('CookieSettingsModalComponent', () => {
  let component: CookieSettingsModalComponent;
  let fixture: ComponentFixture<CookieSettingsModalComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [CookieSettingsModalComponent]
    });
    fixture = TestBed.createComponent(CookieSettingsModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
