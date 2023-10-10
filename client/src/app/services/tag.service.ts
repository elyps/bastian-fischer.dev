// tag.service.ts
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

import { Tag } from '../models/tag.model';

@Injectable({
  providedIn: 'root'
})
export class TagService {

  private baseUrl = 'http://localhost:8083/api/v1/articles/tags'; // Ersetzen Sie dies durch Ihre tatsächliche Backend-URL

  constructor(private http: HttpClient) { }

  getAllTags(): Observable<Tag[]> {
    return this.http.get<Tag[]>(`${this.baseUrl}/all`);
  }

  // Methode, um Tags für den aktuellen Artikel und Titel abzurufen
  getTagsForArticleAndTitle(articleId: number, title: string | null): Observable<string[]> {
    // Passen Sie die API-Anfrage an, um die Tags für den Artikel und Titel abzurufen
    const url = `${this.baseUrl}/tags?articleId=${articleId}&title=${title}`;
    return this.http.get<string[]>(url);
  }

}
