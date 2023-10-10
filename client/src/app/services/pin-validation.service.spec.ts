import { TestBed } from '@angular/core/testing';

import { PinValidationService } from './pin-validation.service';

describe('PinValidationService', () => {
  let service: PinValidationService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(PinValidationService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
