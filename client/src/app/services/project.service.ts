import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Project } from '../models/project.model';

const baseUrl = 'http://localhost:8083/api/v1/projects';

@Injectable({
  providedIn: 'root',
})
export class ProjectService {

  getProjectIdBySlug(slug: string) {
    return this.http.get<number>(`${baseUrl}/id/${slug}`);
  }

  getProjectBySlug(slug: string) {
    return this.http.get<Project>(`${baseUrl}/slug/${slug}`);
  }

  constructor(private http: HttpClient) {}

  /*getAll(): Observable<Article[]> {
    return this.http.get<Article[]>(`${baseUrl}/all`, { params });
  }*/

  getAll(params: any): Observable<any> {
    return this.http.get<any>(`${baseUrl}/all`, { params });
  }

  get(id: any): Observable<Project> {
    return this.http.get(`${baseUrl}/${id}`);
  }

  create(data: any): Observable<any> {
    console.log('Data before sending:', data);
    return this.http.post(`${baseUrl}/add`, data);
  }

  update(id: any, data: any): Observable<any> {
    return this.http.put(`${baseUrl}/${id}`, data);
  }

  delete(id: any): Observable<any> {
    return this.http.delete(`${baseUrl}/${id}`);
  }

  deleteAll(): Observable<any> {
    return this.http.delete(baseUrl);
  }

  findByTitle(title: any): Observable<Project[]> {
    return this.http.get<Project[]>(`${baseUrl}?title=${title}`);
  }

  getArticleUrl(title: string | undefined) {
    return title?.replace(/ /g, '-');
  }

  // getCommentsForArticle(articleId: number): Observable<Comment[]> {
  //   return this.http.get<Comment[]>(`${baseUrl}/${projectId}/comments`);
  // }
}
