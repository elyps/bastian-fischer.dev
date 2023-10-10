import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-version',
  templateUrl: './version.component.html',
  styleUrls: ['./version.component.scss'],
})
export class VersionComponent implements OnInit {
  private baseUrl = 'http://localhost:8083/api/v1/version';
  buildNumber: number = 0;

  constructor(private http: HttpClient) {}

  ngOnInit() {
    this.http.get<number>(`${this.baseUrl}/build/number`).subscribe((data) => {
      this.buildNumber = data;
    });
  }
}
