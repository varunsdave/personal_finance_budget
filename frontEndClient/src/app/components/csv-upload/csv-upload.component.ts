import {Component, Inject, OnInit, ViewChild} from '@angular/core';
import {FileUploadService} from "../../services/file-upload-service";
import {Transaction} from "../../model/transaction";
import {
  MAT_DIALOG_DATA,
  MatDialog,
  MatDialogRef,
  MatPaginator,
  MatSort,
  MatTableDataSource,
  Sort
} from "@angular/material";
import {ConfirmCategorizationActionDialogComponent} from "../confirm-categorization-action-dialog/confirm-categorization-action-dialog.component";
import {CreateNewCategoryDialogComponent} from "../create-new-category-dialog/create-new-category-dialog.component";

@Component({
  selector: 'app-csv-upload',
  templateUrl: './csv-upload.component.html',
  styleUrls: ['./csv-upload.component.scss']
})
export class CsvUploadComponent implements OnInit {

  transactions: Transaction[] = [];
  datasource: MatTableDataSource<Transaction>;
  sortedTransaction: Transaction[] = [];
  displayedColumns: string[];
  @ViewChild(MatSort) sort: MatSort;
  @ViewChild(MatPaginator) paginator: MatPaginator;

  constructor(private fileUploadService: FileUploadService,
              public dialog: MatDialog) { }

  ngOnInit() {
    this.displayedColumns = ["Date", "Type", "Description", "Amount", "Category"];
  }

  readCsvTransactionData(event) {
    if(event && event.files) {
      let file: File = event.files.item(0);
      console.log("clicked on choose file: " + file.name);
      let fileReader: FileReader = new FileReader();
      fileReader.readAsText(file);
      fileReader.onload = (data) => {
        let csv: string = fileReader.result as string;
        console.log(csv);
        this.transactions = this.fileUploadService.readFile(csv);
        this.sortedTransaction = this.transactions;
        this.datasource = new MatTableDataSource(this.sortedTransaction);
      }
    }
  }

  uploadTransactions() {
    const dialogRef= this.dialog.open(ConfirmCategorizationActionDialogComponent, {
      width: '500px',
      data: false
    });

    dialogRef.afterClosed().subscribe((result) => {
      if (result) {
        this.fileUploadService.uploadTransactions(this.transactions, "5d194ce86abd454d0c32aa8b").subscribe(data => {
          this.transactions = data;
          this.sortedTransaction = data;
          this.datasource = new MatTableDataSource(this.sortedTransaction);
        });
      }
    });
  }

  createNewGroupFromFilter() {
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

  applyFilter(filterValue: string) {
    this.datasource.filter = filterValue.trim().toLowerCase();
    if (this.datasource.paginator) {
      this.datasource.paginator.firstPage();
    }
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
        case 'type':
          return compare(a.type, b.type, isAsc);
        case 'amount':
          return compare(a.amount, b.amount, isAsc);
        case 'description':
          return compare(a.description, b.description, isAsc);
        case 'transactionDate':
          return compare(a.transactionDate, b.transactionDate, isAsc);
        default:
          return 0;
      }
    });
  }


}

function compare(a: number | string, b: number | string, isAsc: boolean) {
  return (a < b ? -1 : 1) * (isAsc ? 1 : -1);
}

