export interface CsvFileMetadata {
  firstRowHeader: boolean;
  amountMetadata: {
    debitColumn: number;
    creditColumn: number;
  };
  debitCreditTogether: boolean;
  dateColumn: number;
  descriptionColumn: number;
}

