import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {TransactionListComponent} from "./components/transaction-list/transaction-list.component";
import {CsvUploadComponent} from "./components/csv-upload/csv-upload.component";
import {AccountOverviewComponent} from "./components/account-overview/account-overview.component";
import {AddAccountComponent} from "./components/add-account/add-account.component";

const routes: Routes = [
  { path: 'transactions', component: TransactionListComponent},
  { path: 'uploadcsv', component: CsvUploadComponent},
  { path: 'account', component: AddAccountComponent},
  { path: 'accountoverview', component: AccountOverviewComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
