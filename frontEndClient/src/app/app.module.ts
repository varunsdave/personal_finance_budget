import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import {HttpClientModule} from '@angular/common/http';
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {RestClientService} from './services/rest-client.service';
import { TransactionListComponent } from './components/transaction-list/transaction-list.component';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import { CsvUploadComponent } from './components/csv-upload/csv-upload.component';
import {FileUploadService} from "./services/file-upload-service";
import { AccountOverviewComponent } from './components/account-overview/account-overview.component';
import {
  MatAutocompleteModule,
  MatButtonModule,
  MatCardModule,
  MatCheckboxModule,
  MatFormFieldModule,
  MatSortModule,
  MatTableModule,
  MatInputModule, MatPaginatorModule, MatDatepickerModule, MatNativeDateModule, MatSelectModule
} from "@angular/material";

@NgModule({
  declarations: [
    AppComponent,
    TransactionListComponent,
    CsvUploadComponent,
    AccountOverviewComponent
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    HttpClientModule,
    AppRoutingModule,
    FormsModule,
    ReactiveFormsModule,
    MatAutocompleteModule,
    MatButtonModule,
    MatTableModule,
    MatCheckboxModule,
    MatCardModule,
    MatInputModule,
    MatNativeDateModule,
    MatSortModule,
    MatFormFieldModule,
    MatPaginatorModule,
    MatDatepickerModule,
    MatSelectModule
  ],
  providers: [RestClientService, FileUploadService],
  bootstrap: [AppComponent]
})
export class AppModule { }
