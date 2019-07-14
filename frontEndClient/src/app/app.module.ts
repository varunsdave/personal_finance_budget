import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import {HttpClientModule} from '@angular/common/http';
import {RestClientService} from './services/rest-client.service';
import { TransactionListComponent } from './components/transaction-list/transaction-list.component';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import { CsvUploadComponent } from './components/csv-upload/csv-upload.component';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {FileUploadService} from "./services/file-upload-service";

@NgModule({
  declarations: [
    AppComponent,
    TransactionListComponent,
    CsvUploadComponent
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    HttpClientModule,
    AppRoutingModule,
    FormsModule,
    ReactiveFormsModule
  ],
  providers: [RestClientService, FileUploadService],
  bootstrap: [AppComponent]
})
export class AppModule { }
