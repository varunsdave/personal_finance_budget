import { Component, OnInit } from '@angular/core';
import {RestClientService} from "../../services/rest-client.service";
import {Account} from "../../model/account";
@Component({
  selector: 'app-account-overview',
  templateUrl: './account-overview.component.html',
  styleUrls: ['./account-overview.component.scss']
})
export class AccountOverviewComponent implements OnInit {

  balance: number;
  accountName: string;
  selectedAccount: Account;
  dailyBalance = [
    [new Date('2019-08-01'), 2500],
    [new Date('2019-08-02'), 2600],
    [new Date('2019-08-03'), 2700],
    [new Date('2019-08-04'), 2800],
    [new Date('2019-08-05'), 2900],
    [new Date('2019-08-06'), 3000],
    [new Date('2019-08-07'), 3100],
    [new Date('2019-08-08'), 3200],
    [new Date('2019-08-09'), 3300],
    [new Date('2019-08-10'), 3400],
    [new Date('2019-08-11'), 3500],
    [new Date('2019-08-12'), 3600],
    [new Date('2019-08-13'), 3700],
    [new Date('2019-08-14'), 3800],
    [new Date('2019-08-15'), 3900],
    [new Date('2019-08-16'), 4000]
  ];

  transactionCategories = [
    ['Savings Transfer', 100],
    ['Credit card 1', 20],
    ['Credit card 2', 40],
    ['Car', 40]
  ];
  accountList: Account[];
  fillerNav = ['Account Overview', 'Accounts', 'Transactions'];
  columnNames = ['Date', 'Balance'];

  constructor(private restClientService: RestClientService) { }

  ngOnInit() {
    this.accountName = "Sample Test Account";
    // this.restClientService.getBalanceByAccount('').subscribe((data) => {
    //   this.balance = data;
    // })
    this.restClientService.getAccounts().subscribe((accounts) => {
      this.accountList = accounts;
    })
  }

  selectAccount(selectedAccountId) {
    this.restClientService.getBalanceByAccount(selectedAccountId).subscribe((data) => {
      this.balance = data;
    })
  }

}
