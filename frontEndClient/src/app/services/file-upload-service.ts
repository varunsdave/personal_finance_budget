import {Injectable} from "@angular/core";
import {Transaction} from "../model/transaction";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";

@Injectable()
export class FileUploadService {
  serverUrl = 'http://localhost:8080/';

  constructor(private httpClient: HttpClient) {}

  public readFile(data: string): Transaction[]{

    let csv: string = data;
    let lineArr: string[] = csv.split("\r\n");
    let mappedTranscation: Transaction[] = this.mapTransactions(lineArr);
    return mappedTranscation;
  }

  public uploadTransactions(transactions: Transaction[], accountId: string): Observable<any> {
    return this.httpClient.post<any>(this.serverUrl + "fileUpload/csv/account/" + accountId,transactions);
  }

  private mapTransactions(inputLines: string[]): Transaction[] {
    const transactions: Transaction[] = [];
    let counter = 1;
    for (let t of inputLines) {
      let fields: string[] = t.split(",");

      if (fields[0] && fields[0].length > 0) {
        let transaction: Transaction = new Transaction();
        transaction.id = counter.toString();
        transaction.transactionDate = new Date(fields[0]).toISOString();
        transaction.description = fields[1].replace( /  +/g, ' ' )  ;
        transaction.amount = (fields[2] && fields[2].length > 0) ? Number(fields[2]) : Number(fields[3]);
        transaction.category = {
          filter: "",
          shortDescription: ""
        };
        transaction.type = (fields[2] && fields[2].length > 0) ? "expense" : "income";

        transactions.push(transaction);
        counter++;
      }

    }
    return transactions;
  }



}
