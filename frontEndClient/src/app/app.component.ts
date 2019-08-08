import { Component } from '@angular/core';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {

  routerNav = ['Account Overview', 'Accounts', 'Transactions'];
  title = 'Track expenses and Balances';
}
