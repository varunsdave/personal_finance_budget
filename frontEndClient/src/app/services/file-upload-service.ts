import {Injectable} from "@angular/core";
import {Transaction} from "../model/transaction";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {CsvFileMetadata} from "../model/csvFileMetadata";

@Injectable()
export class FileUploadService {
  serverUrl = 'http://localhost:8080/';

  constructor(private httpClient: HttpClient) {}

  public readFile(data: string, csvFileMetadata: CsvFileMetadata): Transaction[]{

    let csv: string = data;
    let lineArr: string[] = [];

    const crlfAttempt = csv.split("\r\n");
    const lfAttempt = csv.split("\n");

    if (crlfAttempt.length < lfAttempt.length) {
      lineArr = lfAttempt;
    } else {
      lineArr = crlfAttempt;
    }

    let mappedTranscation: Transaction[] = this.mapTransactions(lineArr, csvFileMetadata);
    return mappedTranscation;
  }

  public uploadTransactions(transactions: Transaction[], accountId: string): Observable<any> {
    return this.httpClient.post<any>(this.serverUrl + "fileUpload/csv/account/" + accountId,transactions);
  }

  private mapTransactions(inputLines: string[], csvFileMetada: CsvFileMetadata): Transaction[] {
    const transactions: Transaction[] = [];

    let counter = 1;
    let startPoint = (csvFileMetada.firstRowHeader) ? 1 : 0;

    for (let i = startPoint; i < inputLines.length; i++) {
      const t = inputLines[i];
      let fields: string[] = t.split(",");
      if (fields.length === 1) {
        break;
      }
      let amountColumn = null;
      if (csvFileMetada.debitCreditTogether) {
        amountColumn = csvFileMetada.amountMetadata.debitColumn - 1;
      } else if (fields[csvFileMetada.amountMetadata.debitColumn - 1].length > 0) {
        amountColumn = csvFileMetada.amountMetadata.debitColumn - 1;
      } else {
        amountColumn = csvFileMetada.amountMetadata.creditColumn - 1;
      }
      let amount = Number(fields[amountColumn]);
      let type =  (amountColumn === csvFileMetada.amountMetadata.debitColumn - 1) ? 'expense' : 'income';

      if (csvFileMetada.debitCreditTogether) {
        if (amount < 0) {
          amount = amount * -1.0;
          type = 'expense';
        } else {
          type = 'income';
        }
      }

      if (fields[0] && fields[0].length > 0) {
        let transaction: Transaction = new Transaction();
        transaction.id = counter.toString();
        transaction.transactionDate = new Date(fields[csvFileMetada.dateColumn -1 ]).toISOString();
        transaction.description = fields[csvFileMetada.descriptionColumn - 1].replace( /  +/g, ' ' )  ;
        transaction.amount = amount;
        transaction.category = {
          filter: "",
          shortDescription: ""
        };
        transaction.type = type;

        transactions.push(transaction);
        counter++;
      }

    }
    return transactions;
  }



}
