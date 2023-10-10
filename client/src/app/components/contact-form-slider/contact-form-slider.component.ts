import {Component} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';

@Component({
  selector: 'app-contact-form-slider',
  templateUrl: './contact-form-slider.component.html',
  styleUrls: ['./contact-form-slider.component.scss']
})
export class ContactFormSliderComponent {
  activeSlideIndex = 0;
  form: FormGroup;

  constructor(private formBuilder: FormBuilder) {
    this.form = this.formBuilder.group({
      name: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      message: ['', Validators.required]
    });
  }

  nextSlide() {
    const currentSlide = this.activeSlideIndex;

    if (currentSlide === 0) {
      const nameControl = this.form.get('name');
      nameControl?.markAsTouched(); // Optional chaining
      if (!nameControl?.value.trim()) {
        // Name is empty
        nameControl?.setErrors({ required: true });
      } else if (nameControl?.valid) {
        this.activeSlideIndex++;
      }
    } else if (currentSlide === 1) {
      const emailControl = this.form.get('email');
      emailControl?.markAsTouched(); // Optional chaining
      if (!emailControl?.value.trim()) {
        // Email is empty
        emailControl?.setErrors({ required: true });
      } else if (emailControl?.valid) {
        this.activeSlideIndex++;
      }
    } else if (currentSlide === 2 && this.form.get('message')?.valid) {
      this.activeSlideIndex++;
    }
  }

  prevSlide() {
    this.activeSlideIndex--;
  }

  clearForm() {
    this.form.reset();
    this.activeSlideIndex = 0; // Jump to the first slide
  }

  clearTextarea() {
    this.form.get('message')?.reset(); // Reset only the textarea control
  }

  clearNameInput() {
    this.form.get('name')?.reset(); // Reset only the textarea control
  }

  clearEmailInput() {
    this.form.get('email')?.reset(); // Reset only the textarea control
  }

}
