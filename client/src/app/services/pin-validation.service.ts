// pin-validation.service.ts
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class PinValidationService {
  private baseUrl = 'http://localhost:8083/api/v1/pin';

  constructor(private http: HttpClient) {}

  validatePin(pin: string): Observable<boolean> {
    // Send a POST request to the Spring Boot back end to validate the PIN
    return this.http.post<boolean>(`${this.baseUrl}`+/validate/+`${pin}`,{ pin });
  }
}
