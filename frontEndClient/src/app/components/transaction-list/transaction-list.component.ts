import {Component, OnInit, ViewChild} from '@angular/core';
import {Transaction} from "../../model/transaction";
import {RestClientService} from "../../services/rest-client.service";
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {MatDialog, MatPaginator, MatSort, MatTableDataSource, Sort} from "@angular/material";
import {CreateNewCategoryDialogComponent} from "../create-new-category-dialog/create-new-category-dialog.component";

@Component({
  selector: 'app-transaction-list',
  templateUrl: './transaction-list.component.html',
  styleUrls: ['./transaction-list.component.scss']
})
export class TransactionListComponent implements OnInit {

  transactions: Transaction[]= [];
  datasource: MatTableDataSource<Transaction>;
  newTransactionForm: FormGroup;
  displayedColumns: string[];
  sortedTransaction: Transaction[] = [];
  maxTodaysDate: Date = new Date();

  @ViewChild(MatSort) sort: MatSort;
  @ViewChild(MatPaginator) paginator: MatPaginator;

  constructor(private restClientService: RestClientService,
              public dialog: MatDialog) {
    this.newTransactionForm = this.createFormGroup();
  }

  ngOnInit() {
    this.displayedColumns = ["Date", "Type", "Description", "Amount"];

    this.restClientService.listTransactionsByAccount("5d194ce86abd454d0c32aa8b")
      .subscribe( transactions => {
        this.transactions = transactions;
        this.sortedTransaction = transactions;
        this.datasource =  new MatTableDataSource(this.sortedTransaction);
        this.datasource.sort = this.sort;
        this.datasource.paginator = this.paginator;

    });
  }

  createFormGroup() {
    return new FormGroup({
      transactionAmount: new FormControl(),
      description: new FormControl(),
      transactionDate: new FormControl({
        value: new Date()
      }, Validators.required),
      transactionType: new FormControl()
    })
  }

  public submitForm() {
    const transactionType = this.newTransactionForm.value;
    switch (transactionType.transactionType) {
      case 'expense': {
        this.addExpense();
        break;
      }
      case 'income': {
        this.addIncome();
        break;
      }
      case 'balance': {
        this.addBalance();
        break;
      }
      default: {
        this.newTransactionForm.markAsDirty();
        break;
      }
    }
    console.log(transactionType);
  }

  sortData(sort: Sort) {
    const data = this.transactions.slice();
    if (!sort.active || sort.direction === '') {
      this.sortedTransaction = data;
      return;
    }

    this.sortedTransaction = data.sort((a, b) => {
      const isAsc = sort.direction === 'asc';
      switch (sort.active) {
        case 'type': return compare(a.type, b.type, isAsc);
        case 'amount': return compare(a.amount, b.amount, isAsc);
        case 'description': return compare(a.description, b.description, isAsc);
        case 'transactionDate': return compare(a.transactionDate, b.transactionDate, isAsc);
        default: return 0;
      }
    });

    this.datasource = new MatTableDataSource(this.sortedTransaction);
  }

  applyFilter(filterValue: string) {
    this.datasource.filter = filterValue.trim().toLowerCase();
    if (this.datasource.paginator) {
      this.datasource.paginator.firstPage();
    }
  }

  createNewGroupFromFilter(filterValue: string) {
    const dialogRef = this.dialog.open(CreateNewCategoryDialogComponent, {
      width: '500px',
      data: {
        filter: this.datasource.filter,
        shortDescription: ''
      }
    });

    dialogRef.afterClosed().subscribe((result) => {
      if (result !== null) {
        this.datasource.filteredData.map((transaction) => {
          transaction.category = result;
        });
        this.transactions = this.datasource.data;
        this.datasource = new MatTableDataSource(this.transactions)
      }
    });
  }


  public addIncome() {
    let newTransaction: Transaction = {
      id: "anyId",
      type : "income",
      amount : this.newTransactionForm.value.transactionAmount,
      description :this.newTransactionForm.value.description,
      transactionDate : new Date(this.newTransactionForm.value.transactionDate).toISOString()
    };

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
    };
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
    };
    this.restClientService
      .addTransaction(newTransaction, "5d194ce86abd454d0c32aa8b")
      .subscribe(t => this.transactions.push(t));
  }

}
function compare(a: number | string, b: number | string, isAsc: boolean) {
  return (a < b ? -1 : 1) * (isAsc ? 1 : -1);
}
