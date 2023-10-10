// cookie-popup.component.ts
import { Component } from '@angular/core';

declare let $: any;

@Component({
  selector: 'app-cookie-popup',
  templateUrl: './cookie-popup.component.html',
  styleUrls: ['./cookie-popup.component.scss'],
})
export class CookiePopupComponent {

  acceptCookies() {
    localStorage.setItem('must-have-cookies', 'true');
    localStorage.setItem('marketing-cookies', 'true');
    localStorage.setItem('analytics-cookies', 'true');

    window.location.reload();

    $('.cookie-modal').modal('hide');
  }
}
