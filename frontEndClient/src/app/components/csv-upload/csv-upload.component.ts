import { Component, OnInit } from '@angular/core';
import {FileUploadService} from "../../services/file-upload-service";
import {Transaction} from "../../model/transaction";

@Component({
  selector: 'app-csv-upload',
  templateUrl: './csv-upload.component.html',
  styleUrls: ['./csv-upload.component.scss']
})
export class CsvUploadComponent implements OnInit {

  transactions: Transaction[] = [];

  constructor(private fileUploadService: FileUploadService) { }

  ngOnInit() {
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
      }
    }
  }

  uploadTransactions() {
    this.fileUploadService.uploadTransactions(this.transactions, "5d194ce86abd454d0c32aa8b").subscribe(data => {
      this.transactions = data;
    });

  }

}
