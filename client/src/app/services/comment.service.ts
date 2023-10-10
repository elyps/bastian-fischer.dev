import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable, throwError} from "rxjs";
import {catchError} from "rxjs/operators";
import {Article} from "../models/article.model";
import {CommentHelper} from "../models/commentHelper.model";

@Injectable({
  providedIn: 'root',
})
export class CommentService {
  private baseUrl = 'http://localhost:8083/api/v1/articles';

  constructor(private http: HttpClient) {
  }

  addComment(content: any, articleId: any): Observable<Comment> {
    console.log('Data before sending:', content);
    return this.http.post<Comment>(`${this.baseUrl}/${articleId}/comments`, content)
  }

  getAll() {
    return this.http.get<Comment[]>(`${this.baseUrl}/all`);
  }
}
