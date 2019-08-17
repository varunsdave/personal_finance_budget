import { Component, OnInit } from '@angular/core';
import {RestClientService} from "../../services/rest-client.service";
import {Account} from "../../model/account";
import * as _ from 'lodash';

@Component({
  selector: 'app-account-overview',
  templateUrl: './account-overview.component.html',
  styleUrls: ['./account-overview.component.scss']
})
export class AccountOverviewComponent implements OnInit {

  balance: number;
  accountName: string;
  selectedAccount: Account;
  currentMonthExpense: number = 0;
  currentMonthIncome: number = 0;
  dailyBalance = [
  ];

  transactionCategories = [
  ];
  accountList: Account[];
  fillerNav = ['Account Overview', 'Accounts', 'Transactions'];
  columnNames = ['Date', 'Balance'];

  constructor(private restClientService: RestClientService) { }

  ngOnInit() {
    this.accountName = "Sample Test Account";
    this.restClientService.getAccounts().subscribe((accounts) => {
      this.accountList = accounts;
    })

  }

  selectAccount(selectedAccountId) {
    this.restClientService.getBalanceByAccount(selectedAccountId).subscribe((data) => {
      this.balance = data;
    });
    this.restClientService.listTransactionsByAccount(selectedAccountId).subscribe((transactions) => {

      const today: Date = new Date();
      const currentYear: number = today.getFullYear();
      const currentMonth: number = today.getMonth();
      this.currentMonthExpense = transactions.filter((t) => {
        return t.type === "expense"
          && new Date(t.transactionDate).getFullYear() === currentYear
          && new Date(t.transactionDate).getMonth() === currentMonth
      }).map(items => items.amount)
        .reduce((prev, current) => prev + current, 0);

      this.currentMonthIncome = transactions.filter((t) => {
        return t.type === "income"
          && new Date(t.transactionDate).getFullYear() === currentYear
          && new Date(t.transactionDate).getMonth() === currentMonth
      }).map(items => items.amount)
        .reduce((prev, current) => prev + current, 0);
      this.dailyBalance = [];
      transactions.forEach((t) => {
        this.dailyBalance.push([new Date(t.transactionDate), t.accountBalance]);
      });

      let groupings: {
        [key: string]: number
      } = _.countBy(transactions, (t) =>  {
        if (t.category.shortDescription === "") {
          return "Other";
        } else {
          return  t.category.shortDescription;
        }
      });
      let sorted = _.chain(groupings)
        .map((count, group) => {
          return {
            group: group,
            count: count
          };
        })
        .sortBy('count')
        .reverse()
        .value();
      this.transactionCategories = sorted.map((item) => {
        return [item.group, item.count];
      });

    })
  }

}

