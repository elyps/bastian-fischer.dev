  import { Component, OnInit, OnDestroy } from '@angular/core';
  import { GitHubService } from '../../services/github.service';
  import { Subscription } from 'rxjs';
  import { Repository } from '../../models/repository.model';
  import { HttpClient } from '@angular/common/http';

  @Component({
    selector: 'app-repositories',
    templateUrl: './repositories.component.html',
    styleUrls: ['./repositories.component.scss'],
  })
  export class RepositoriesComponent implements OnInit, OnDestroy {
    public repository: Repository[] = [];

    private subscription: Subscription = new Subscription();

    repositoryList: any;
    username: any = 'elyps';
    htmlUrl: any = 'https://github.com/' + this.username;

    constructor(private gitHubService: GitHubService, private http: HttpClient) {}

    ngOnInit() {
      this.retrieveRepositories();
    }

    ngOnDestroy() {
      this.subscription.unsubscribe();
    }

    /* retrieveRepositories(): void {
      this.http
        .get<Repository[]>(`http://localhost:8083/api/v1/github/repos`)
        .subscribe((repository: Repository[]) => {
          this.repository = Array.isArray(repository) ? repository : [repository];
          console.log(repository);
        });
    } */

    retrieveRepositories(): void {
      this.subscription.add(
        this.gitHubService.fetchRepositories().subscribe((repository: Repository[]) => {
          this.repository = Array.isArray(repository) ? repository : [repository];
          console.log(repository);
        })
      );
    }

  }

