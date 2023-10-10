// pin-input.component.ts
import {Component, Output, EventEmitter, OnInit} from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import {PinValidationService} from "../../services/pin-validation.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-pin-input',
  templateUrl: './pin-input.component.html',
  styleUrls: ['./pin-input.component.scss']
})
export class PinInputComponent implements OnInit {
  @Output() pinEntered = new EventEmitter<string>();
  pinForm: FormGroup;
  isInvalidPinLength: any;
  isSuccessMessageVisible: any;

  constructor(private formBuilder: FormBuilder, private pinValidationService: PinValidationService, private router: Router) {
    this.pinForm = this.formBuilder.group({
      pin: ['', Validators.required]
    });
  }

  ngOnInit(): void {
    let pin = localStorage.getItem('pin'); // Replace with your code to get the PIN from local storage.
    if (pin) {
      this.router.navigate(['/recruiter/dashboard']).then(r => console.log(r));
    }
  }

  validatePin() {
    // console.log('Button clicked');
    if (this.pinForm.valid && this.pinForm.get('pin')?.value.length === 4) {
      console.log('Form is valid');
      this.pinEntered.emit(this.pinForm.get('pin')?.value);
      this.pinValidationService.validatePin(this.pinForm.get('pin')?.value).subscribe(
        (response) => {
          console.log(response);
          if (response === true) {
            console.log('Pin is valid');
            this.isInvalidPinLength = false;
            this.isSuccessMessageVisible = true;
            // save to local storage
            // localStorage.setItem('pin', this.pinForm.get('value')?.value);
            localStorage.setItem('pin', 'valid');
            // navigate to the next page
            this.router.navigate(['/recruiter/dashboard']);
          } else {
            console.log('Pin is invalid');
            this.isInvalidPinLength = true;
            this.isSuccessMessageVisible = false;
            localStorage.setItem('pin', 'invalid');
          }
        },
        (error) => {
          console.log(error);
          console.log('Pin Error');
          this.isInvalidPinLength = true;
          this.isSuccessMessageVisible = false;
        }
      );
    } else {
      console.log('Form is invalid');
    }
  }

}
