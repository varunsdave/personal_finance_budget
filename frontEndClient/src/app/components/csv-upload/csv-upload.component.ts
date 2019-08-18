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
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {Account} from "../../model/account";
import {RestClientService} from "../../services/rest-client.service";
import {CsvFileMetadata} from "../../model/csvFileMetadata";

@Component({
  selector: 'app-csv-upload',
  templateUrl: './csv-upload.component.html',
  styleUrls: ['./csv-upload.component.scss']
})
export class CsvUploadComponent implements OnInit {

  uploadCsvForm: FormGroup;
  firstRowHeader: FormControl;
  debitCreditTogether: FormControl;
  dateColumn: FormControl;
  descriptionColumn: FormControl;
  amountColumn: FormControl;
  debitColumn: FormControl;
  creditColumn: FormControl;



  showSingleAmountInput: boolean;
  showMetadataFormSections: boolean;
  fileParseComplete: boolean;

  transactions: Transaction[] = [];
  datasource: MatTableDataSource<Transaction>;
  sortedTransaction: Transaction[] = [];
  displayedColumns: string[];

  fileContents: string;
  selectedAccount: Account;
  accountList: Account[];

  @ViewChild(MatSort) sort: MatSort;
  @ViewChild(MatPaginator) paginator: MatPaginator;

  constructor(private fileUploadService: FileUploadService,
              private restClientService: RestClientService,
              public dialog: MatDialog) { }

  ngOnInit() {
    this.displayedColumns = ["Date", "Type", "Description", "Amount", "Category"];
    this.restClientService.getAccounts().subscribe((accounts) => {
      this.accountList = accounts;
    });
    this.uploadCsvForm = this.setupUploadFileForm();
    this.showSingleAmountInput = false;
    this.showMetadataFormSections = false;
    this.fileParseComplete = false;
  }

  readCsvTransactionData(event) {
    if(event && event.files) {
      let file: File = event.files.item(0);
      console.log("clicked on choose file: " + file.name);
      this.showMetadataFormSections = true;
      let fileReader: FileReader = new FileReader();
      fileReader.readAsText(file);
      fileReader.onload = (data) => {
        this.fileContents = fileReader.result as string;;
      }
    }
  }

  parseFileAndPreview() {
    if (!this.uploadCsvForm.valid) {
      window.alert('please input all values');
      return;
    }
    if (this.showMetadataFormSections) {
      let csv: string = this.fileContents;
      console.log(csv);
      const csvFileMetadata: CsvFileMetadata = {
        firstRowHeader: this.uploadCsvForm.value.firstRowHeader,
        descriptionColumn: this.uploadCsvForm.value.descriptionColumn,
        dateColumn: this.uploadCsvForm.value.dateColumn,
        debitCreditTogether: this.uploadCsvForm.value.debitCreditTogether,
        amountMetadata: {
          debitColumn: (this.uploadCsvForm.value.debitCreditTogether) ? this.uploadCsvForm.value.amountColumn : this.uploadCsvForm.value.debitColumn,
          creditColumn: (this.uploadCsvForm.value.debitCreditTogether) ? this.uploadCsvForm.value.amountColumn : this.uploadCsvForm.value.creditColumn
        }
      };
      this.transactions = this.fileUploadService.readFile(csv, csvFileMetadata);
      this.sortedTransaction = this.transactions;
      this.datasource = new MatTableDataSource(this.sortedTransaction);

      this.showMetadataFormSections = false;
      this.fileParseComplete = true;
    }
  }

  uploadTransactions() {

    if (this.transactions.length === 0) {
      this.uploadCsvForm.markAsDirty();
      return;
    }
    const dialogRef= this.dialog.open(ConfirmCategorizationActionDialogComponent, {
      width: '500px',
      data: false
    });

    dialogRef.afterClosed().subscribe((result) => {
      if (result) {
        this.fileUploadService.uploadTransactions(this.transactions, this.uploadCsvForm.value.accountName).subscribe(data => {
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

  public incomeExpenseCheckboxChange(n) {
    this.showSingleAmountInput = n;
    if (this.showSingleAmountInput) {
      this.uploadCsvForm.controls.debitColumn.setValidators(Validators.nullValidator);
      this.uploadCsvForm.controls.debitColumn.disable();
      this.uploadCsvForm.controls.creditColumn.setValidators(Validators.nullValidator);
      this.uploadCsvForm.controls.creditColumn.disable();
      this.uploadCsvForm.controls.amountColumn.setValidators(Validators.required);
    } else {
      this.uploadCsvForm.controls.debitColumn.setValidators(Validators.required);
      this.uploadCsvForm.controls.creditColumn.setValidators(Validators.required);
      this.uploadCsvForm.controls.amountColumn.setValidators(Validators.nullValidator);
    }

  }

  private setupUploadFileForm(): FormGroup {
    return new FormGroup({
      accountName: new FormControl(),
      fileUploadCtrl: new FormControl(),
      firstRowHeader: new FormControl(),
      debitCreditTogether: new FormControl(),
      dateColumn: new FormControl(),
      descriptionColumn: new FormControl(),
      amountColumn: new FormControl(),
      debitColumn: new FormControl(),
      creditColumn: new FormControl()
    });
  }

}

function compare(a: number | string, b: number | string, isAsc: boolean) {
  return (a < b ? -1 : 1) * (isAsc ? 1 : -1);
}
