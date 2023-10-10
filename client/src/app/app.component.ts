import { Component, OnInit } from '@angular/core';
import { Subscription } from 'rxjs';
import { StorageService } from './services/storage.service';
import { AuthService } from './services/auth.service';
import { EventBusService } from './shared/event-bus.service';
import { ThemeService } from './services/theme.service';
import { CookieService } from 'ngx-cookie-service';

declare var $: any;

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss'],
})
export class AppComponent implements OnInit {
  private roles: string[] = [];
  isLoggedIn = false;
  showAdminBoard = false;
  showModeratorBoard = false;
  showActions = false;
  username?: string;

  eventBusSub?: Subscription;
  currentArticle: any;

  darkMode = false;
  title: string | undefined;
  toggleStatus: boolean = false;

  constructor(
    private storageService: StorageService,
    private authService: AuthService,
    private eventBusService: EventBusService,
    private themeService: ThemeService,
    private cookieService: CookieService
  ) {}

  ngOnInit(): void {
    // Beim Initialisieren die Auswahl des Modus aus dem Local Storage abrufen
    this.darkMode = this.themeService.getDarkMode();

    if (this.darkMode) {
      document.body.classList.add('dark-mode');
    } else {
      document.body.classList.remove('dark-mode');
    }

    // Beim Initialisieren prüfen, ob der Benutzer angemeldet ist
    this.isLoggedIn = this.storageService.isLoggedIn();

    if (this.isLoggedIn) {
      const user = this.storageService.getUser();
      this.roles = user.roles;

      this.showAdminBoard = this.roles.includes('ROLE_ADMIN');
      this.showModeratorBoard =
        this.roles.includes('ROLE_MODERATOR') ||
        this.roles.includes('ROLE_ADMIN');
      this.showActions =
        this.roles.includes('ROLE_MODERATOR') ||
        this.roles.includes('ROLE_ADMIN');

      this.username = user.username;
    }

    // Beim Initialisieren auf das Event 'logout' hören
    this.eventBusSub = this.eventBusService.on('logout', () => {
      this.logout();
    });

    // Beim Initialisieren Cookies prüfen
    const cookieData = localStorage.getItem('must-have-cookies');
    const cookieData1 = localStorage.getItem('marketing-cookies');
    const cookieData2 = localStorage.getItem('analytics-cookies');

    // Wenn der Benutzer die Cookies noch nicht akzeptiert hat, das Modal anzeigen
    if (!cookieData && !cookieData1 && !cookieData2) {
      // console.log(`Cookies: ${cookieData}`);
      $('.cookie-modal').modal('show');
    }

    // this.toggleSwitch();

  }

  // toggleSwitch() {
  //   this.toggleStatus = !this.toggleStatus;

  //   if (!this.toggleStatus) {
  //     localStorage.removeItem('cookies');
  //     this.toggleStatus = false;
  //   } else {
  //     localStorage.setItem('cookies', 'true');
  //     this.toggleStatus = true;
  //   }
  // }

  logout(): void {
    this.storageService.clean();
    this.authService.logout().subscribe({
      next: (res) => {
        console.log(res);
        // window.location.reload();
        window.location.href = '/';
      },
      error: (err) => {
        console.log(err);
      },
    });
  }

  toggleDarkMode(): void {
    // Den ausgewählten Modus umschalten
    this.darkMode = !this.darkMode;

    // Den ausgewählten Modus in den Local Storage speichern
    this.themeService.setDarkMode(this.darkMode);

    // Logik für das Aktivieren/Deaktivieren des Dark Modes
    if (this.darkMode) {
      document.body.classList.add('dark-mode');
    } else {
      document.body.classList.remove('dark-mode');
    }
  }
}
