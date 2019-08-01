import { Component, OnInit } from '@angular/core';
import {Transaction} from "../../model/transaction";
import {RestClientService} from "../../services/rest-client.service";
import {FormControl, FormGroup} from "@angular/forms";

@Component({
  selector: 'app-transaction-list',
  templateUrl: './transaction-list.component.html',
  styleUrls: ['./transaction-list.component.scss']
})
export class TransactionListComponent implements OnInit {

  transactions: Transaction[];
  newTransactionForm: FormGroup;
  constructor(private restClientService: RestClientService) { }

  ngOnInit() {
    this.newTransactionForm = this.createFormGroup();

    this.restClientService.listTransactionsByAccount("5d194ce86abd454d0c32aa8b")
      .subscribe( transactions => {
        this.transactions = transactions;
    });
  }

  createFormGroup() {
    return new FormGroup({
      transactionAmount: new FormControl(),
      description: new FormControl(),
      transactionDate: new FormControl()
    })
  }

  public addIncome() {
      let newTransaction: Transaction = {
          id: "anyId",
          type : "income",
          amount : this.newTransactionForm.value.transactionAmount,
          description :this.newTransactionForm.value.description,
          transactionDate : new Date(this.newTransactionForm.value.transactionDate).toISOString()
      }

      this.restClientService
        .addTransaction(newTransaction, "5d194ce86abd454d0c32aa8b")
        .subscribe(t => this.transactions.push(t));
  }

  public addExpense() {
    let newTransaction: Transaction = {
      id: "anyId",
      type : "expense",
      amount : this.newTransactionForm.value.transactionAmount,
      description :this.newTransactionForm.value.description,
      transactionDate : new Date(this.newTransactionForm.value.transactionDate).toISOString()
    }
    this.restClientService
      .addTransaction(newTransaction, "5d194ce86abd454d0c32aa8b")
      .subscribe(t => this.transactions.push(t));
  }

  public addBalance() {
    let newTransaction: Transaction = {
      id: "anyId",
      type : "balance",
      amount : this.newTransactionForm.value.transactionAmount,
      description :this.newTransactionForm.value.description,
      transactionDate : new Date(this.newTransactionForm.value.transactionDate).toISOString()
    }
    this.restClientService
      .addTransaction(newTransaction, "5d194ce86abd454d0c32aa8b")
      .subscribe(t => this.transactions.push(t));
  }

}
