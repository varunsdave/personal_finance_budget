import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material";

export interface TransactionCategory {
  filter: string;
  shortDescription: string;
}

@Component({
  selector: 'app-create-new-category-dialog',
  templateUrl: './create-new-category-dialog.component.html',
  styleUrls: ['./create-new-category-dialog.component.scss']
})
export class CreateNewCategoryDialogComponent implements OnInit {

  constructor(
    public dialogRef: MatDialogRef<CreateNewCategoryDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public category: TransactionCategory
  ) { }

  ngOnInit() {
  }

  onNoClick() {
    this.dialogRef.close(null);
  }

}
