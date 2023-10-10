import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class GitHubService {
  private baseUrl = 'http://localhost:8083/api/v1/github';

  constructor(private http: HttpClient) {}

  loadAndSaveRepos() {
    return this.http.post<any[]>(`${this.baseUrl}/repositories/save`, null);
  }

  fetchRepositories() {
    return this.http.get<any[]>(`${this.baseUrl}/repositories`);
  }

  fetchRepository(id: number) {
    return this.http.get<any[]>(`${this.baseUrl}/repos/${id}`);
  }

}
