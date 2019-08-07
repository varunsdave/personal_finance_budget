import {Component, OnInit, ViewChild} from '@angular/core';
import {Form, FormControl, FormGroup} from "@angular/forms";
import {RestClientService} from "../../services/rest-client.service";
import {Account} from "../../model/account";
import {MatPaginator, MatSort, MatTableDataSource} from "@angular/material";

@Component({
  selector: 'app-add-account',
  templateUrl: './add-account.component.html',
  styleUrls: ['./add-account.component.scss']
})
export class AddAccountComponent implements OnInit {

  newAccountForm: FormGroup;

  accountNameCtrl: FormControl;
  bankCtrl: FormControl;
  accountTypeCtrl: FormControl;
  displayedColumns: string[];

  accounts: Account[] = [];
  acountsDs: MatTableDataSource<Account>;
  @ViewChild(MatSort) sort: MatSort;
  @ViewChild(MatPaginator) paginator: MatPaginator;

  constructor(private restClientService: RestClientService) { }

  ngOnInit() {
    this.displayedColumns = ["Name", "Date", "Id", "Bank", "Purpose"];
    this.newAccountForm = this.setupFormGroup();
    this.restClientService.getAccounts().subscribe((accounts) => {
      this.accounts = accounts;
      this.acountsDs = new MatTableDataSource(accounts);
    })
  }

  public submitForm() {
    console.log(this.newAccountForm.value.accountNameCtrl);
    const newAccount: Account = {
      id: "",
      name: this.newAccountForm.value.accountNameCtrl,
      createdAt: new Date(),
      bank: this.newAccountForm.value.bankCtrl,
      purpose: this.newAccountForm.value.accountTypeCtrl
    };
    this.restClientService.createAccount(newAccount).subscribe((data) => {
      const accountsCopy: Account[] = JSON.parse(JSON.stringify(this.accounts));
      accountsCopy.push(data);
      this.acountsDs = new MatTableDataSource(accountsCopy);
    });
  }

  private setupFormGroup(): FormGroup {
    return new FormGroup({
      accountNameCtrl: new FormControl(),
      bankCtrl: new FormControl(),
      accountTypeCtrl: new FormControl()
    });
  }

}
