import { Component, OnInit } from '@angular/core';
import {RestClientService} from "../../services/rest-client.service";

@Component({
  selector: 'app-account-overview',
  templateUrl: './account-overview.component.html',
  styleUrls: ['./account-overview.component.scss']
})
export class AccountOverviewComponent implements OnInit {

  balance: number;

  constructor(private restClientService: RestClientService) { }

  ngOnInit() {
    this.restClientService.getBalanceByAccount('').subscribe((data) => {
      this.balance = data;
    })
  }

}
