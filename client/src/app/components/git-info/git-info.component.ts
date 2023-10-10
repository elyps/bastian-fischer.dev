import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-git-info',
  templateUrl: './git-info.component.html',
  styleUrls: ['./git-info.component.scss'],
})
export class GitInfoComponent implements OnInit {
  private baseUrl = 'http://localhost:8083/api/v1/git';
  commitId: string = '';

  constructor(private http: HttpClient) {}

  ngOnInit(): void {
   this.http
     .get(`${this.baseUrl}/commit/id`, { responseType: 'text' })
     .subscribe(
       (response) => {
         this.commitId = response; // Assign the plain text response directly
       },
       (error) => {
         console.error('Error fetching commit ID:', error);
         // Handle the HTTP request error here.
       }
     );


  }

}
