import { Component, OnInit } from '@angular/core';
import { UserService } from '../../services/user.service';
import { StorageService } from '../../services/storage.service';
import { AuthService } from '../../services/auth.service';
import { GitHubService } from '../../services/github.service';
import { Subscription } from 'rxjs';
import { Repository } from '../../models/repository.model';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-board-admin',
  templateUrl: './board-admin.component.html',
  styleUrls: ['./board-admin.component.scss'],
})
export class BoardAdminComponent implements OnInit {
  content?: string;

  private roles: string[] = [];
  isLoggedIn = false;
  showAdminBoard = false;
  showModeratorBoard = false;
  showActions = false;
  username?: string;
  githubProperties: any;

  public repository: Repository[] = [];
  private subscription: Subscription = new Subscription();

  constructor(
    private userService: UserService,
    private storageService: StorageService,
    private authService: AuthService,
    private gitHubService: GitHubService,
    private http: HttpClient
  ) {}

  ngOnInit(): void {
    this.userService.getAdminBoard().subscribe({
      next: (data) => {
        this.content = data;
      },
      error: (err) => {
        console.log(err);
        if (err.error) {
          this.content = JSON.parse(err.error).message;
        } else {
          this.content = 'Error with status: ' + err.status;
        }
      },
    });

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
  }

  retrieveRepositories(): void {
    this.subscription.add(
      this.gitHubService
        .loadAndSaveRepos()
        .subscribe((data) => (this.repository = data))
    );
    console.log(
      'Delete old repositories ... done!\nFetch new ones ... done!\nSave new ones ... done!\nAll new repositories from user elyps saved to database successfully!'
    );
  }
}
