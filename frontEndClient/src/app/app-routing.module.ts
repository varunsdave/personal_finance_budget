import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {TransactionListComponent} from "./components/transaction-list/transaction-list.component";
import {CsvUploadComponent} from "./components/csv-upload/csv-upload.component";

const routes: Routes = [
  { path: 'transactions', component: TransactionListComponent},
  { path: 'uploadcsv', component: CsvUploadComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
