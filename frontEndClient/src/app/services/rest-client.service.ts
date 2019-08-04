import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Transaction} from "../model/transaction";

@Injectable()
export class RestClientService {
  serverUrl = 'http://localhost:8080/'

  constructor(private http: HttpClient) {
  }

  public getAccounts(): Observable<any> {
    return this.http.get(this.serverUrl + '/account');
  }

  public createAccount(accountName: string): Observable<Account[]> {
    return this.http.post<Account[]>( this.serverUrl + '/account', accountName);
  }

  public listTransactionsByUser(accountId: string): Observable<Transaction[]> {
    return this.http.get<Transaction[]>( this.serverUrl + '/user/testuser/transactions');
  }

  public listTransactionsByAccount(accountId: string): Observable<Transaction[]> {
    return this.http.get<Transaction[]>( this.serverUrl + '/user/testuser/transactions/account/' + accountId);
  }

  public addTransaction(transaction: Transaction, accountId: string): Observable<Transaction> {
    transaction.accountId = accountId;
    return this.http.post<Transaction>(this.serverUrl + transaction.type + '/account/' + accountId, transaction, {
      headers: {
        'Content-Type': 'application/json'
      }
    });
  }

  public getBalanceByAccount(accountId: string): Observable<number> {
    return this.http.get<number>(this.serverUrl + 'balance/currentBalance/account/' + "5d194ce86abd454d0c32aa8b");
  }
}