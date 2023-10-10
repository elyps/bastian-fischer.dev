import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GithubBannerComponent } from './github-banner.component';

describe('GithubBannerComponent', () => {
  let component: GithubBannerComponent;
  let fixture: ComponentFixture<GithubBannerComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [GithubBannerComponent]
    });
    fixture = TestBed.createComponent(GithubBannerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
