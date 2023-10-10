import { Component } from '@angular/core';

@Component({
  selector: 'app-footer',
  templateUrl: './footer.component.html',
  styleUrls: ['./footer.component.scss'],
})
export class FooterComponent {
  date: Date = new Date();

  year: number = this.date.getFullYear();

  // buildNumber: string = 'yy.d.m.h.m.s';
  buildNumber: string = 'yydm'
    .replace('yy', this.date.getFullYear().toString().substr(-2))
    .replace('d', this.date.getDate().toString())
    .replace('m', (this.date.getMonth() + 1).toString());
    /* .replace('h', this.date.getHours().toString())
    .replace('m', this.date.getMinutes().toString())
    .replace('s', this.date.getSeconds().toString()); */

    appVersion: string = '1.0.0';

    apiVersion: string = '1.0.0';

}
