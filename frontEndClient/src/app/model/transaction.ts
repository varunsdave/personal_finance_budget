import {TransactionCategory} from "../components/create-new-category-dialog/create-new-category-dialog.component";

export class Transaction {
  id: string;
  amount: number;
  transactionDate: string;
  type: string;
  description: string;
  accountId?: string;
  accountBalance?: number;
  category?: TransactionCategory;
}
