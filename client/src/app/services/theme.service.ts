import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class ThemeService {
  private darkModeKey = 'darkMode';

  constructor() { }

  getDarkMode(): boolean {
    const darkModeValue = localStorage.getItem(this.darkModeKey);
    return darkModeValue ? JSON.parse(darkModeValue) : false;
  }

  setDarkMode(darkMode: boolean): void {
    localStorage.setItem(this.darkModeKey, JSON.stringify(darkMode));
  }

}
