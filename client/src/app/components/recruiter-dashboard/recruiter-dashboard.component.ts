import {Component, OnInit} from '@angular/core';
import {Router} from "@angular/router";

@Component({
  selector: 'app-recruiter-dashboard',
  templateUrl: './recruiter-dashboard.component.html',
  styleUrls: ['./recruiter-dashboard.component.scss']
})
export class RecruiterDashboardComponent implements OnInit {

  constructor(private router: Router) {
  }

  ngOnInit(): void {
    let pin = localStorage.getItem('pin'); // Replace with your code to get the PIN from local storage.
    if (!pin) {
      this.router.navigate(['/recruiter']).then(r => console.log(r));
    }
  }

  logout() {
    localStorage.removeItem('pin');
    this.router.navigate(['/recruiter']).then(r => console.log(r));
  }

}
