import { Component, OnInit } from '@angular/core';

declare let $: any;

@Component({
  selector: 'app-cookie-settings-modal',
  templateUrl: './cookie-settings-modal.component.html',
  styleUrls: ['./cookie-settings-modal.component.scss'],
})
export class CookieSettingsModalComponent implements OnInit {
  constructor() {}

  toggleStatusMustHave: boolean = false;
  toggleStatusMarketing: boolean = false;
  toggleStatusAnalytics: boolean = false;

  ngOnInit() {
    const isMustHaveCookieSet = localStorage.getItem('must-have-cookies');
    const isMarketingCookieSet = localStorage.getItem('marketing-cookies');
    const isAnalyticsCookieSet = localStorage.getItem('analytics-cookies');

    if (isMustHaveCookieSet) {
      this.toggleStatusMustHave = true; // Schalter auf ON setzen
      localStorage.setItem('must-have-cookies', 'true');
    }

    if (isMarketingCookieSet) {
      this.toggleStatusMarketing = true; // Schalter auf ON setzen
      localStorage.setItem('marketing-cookies', 'true');
    }

    if (isAnalyticsCookieSet) {
      this.toggleStatusAnalytics = true; // Schalter auf ON setzen
      localStorage.setItem('analytics-cookies', 'true');
    }
  }

  // Hier können Sie eine Funktion hinzufügen, um den Schalterzustand zu ändern
  toggleSwitchMustHave() {
    this.toggleStatusMustHave = !this.toggleStatusMustHave;

    if (this.toggleStatusMustHave) {
      localStorage.setItem('must-have-cookies', 'true');
    } else {
      localStorage.removeItem('must-have-cookies');
    }

  }

  toggleSwitchMarketing() {
    this.toggleStatusMarketing = !this.toggleStatusMarketing;

    if (this.toggleStatusMarketing) {
      localStorage.setItem('marketing-cookies', 'true');
    } else {
      localStorage.removeItem('marketing-cookies');
    }

  }

  toggleSwitchAnalytics() {
    this.toggleStatusAnalytics = !this.toggleStatusAnalytics;

    if (this.toggleStatusAnalytics) {
      localStorage.setItem('analytics-cookies', 'true');
    } else {
      localStorage.removeItem('analytics-cookies');
    }
  }

  closeModal() {
    $('.cookie-settings-modal').modal('hide');
  }

  openModal() {
    $('.cookie-settings-modal').modal('show');
  }
}
