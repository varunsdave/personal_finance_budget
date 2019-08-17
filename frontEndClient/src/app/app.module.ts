import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import {HttpClientModule} from '@angular/common/http';
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {RestClientService} from './services/rest-client.service';
import { TransactionListComponent } from './components/transaction-list/transaction-list.component';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {CsvUploadComponent} from './components/csv-upload/csv-upload.component';
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
  MatInputModule,
  MatPaginatorModule,
  MatDatepickerModule,
  MatNativeDateModule,
  MatSelectModule,
  MatRadioModule,
  MatToolbarModule, MatSidenavModule, MatIconModule, MatListModule, MatDialogModule
} from "@angular/material";
import { AddAccountComponent } from './components/add-account/add-account.component';
import {GoogleChartsModule} from "angular-google-charts";
import { ConfirmCategorizationActionDialogComponent } from './components/confirm-categorization-action-dialog/confirm-categorization-action-dialog.component';
import { CreateNewCategoryDialogComponent } from './components/create-new-category-dialog/create-new-category-dialog.component';

@NgModule({
  declarations: [
    AppComponent,
    TransactionListComponent,
    CsvUploadComponent,
    AccountOverviewComponent,
    AddAccountComponent,
    ConfirmCategorizationActionDialogComponent,
    CreateNewCategoryDialogComponent
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
    MatSelectModule,
    MatRadioModule,
    GoogleChartsModule.forRoot(),
    MatToolbarModule,
    MatSidenavModule,
    MatIconModule,
    MatListModule,
    MatDialogModule
  ],
  entryComponents: [ConfirmCategorizationActionDialogComponent, CreateNewCategoryDialogComponent],
  providers: [RestClientService, FileUploadService],
  bootstrap: [AppComponent]
})
export class AppModule { }
