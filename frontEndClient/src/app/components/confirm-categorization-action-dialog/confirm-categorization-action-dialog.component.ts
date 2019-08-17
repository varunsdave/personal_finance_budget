import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material";

@Component({
  selector: 'app-confirm-categorization-action-dialog',
  templateUrl: './confirm-categorization-action-dialog.component.html',
  styleUrls: ['./confirm-categorization-action-dialog.component.scss']
})
export class ConfirmCategorizationActionDialogComponent implements OnInit {

  constructor( public dialogRef: MatDialogRef<ConfirmCategorizationActionDialogComponent>,
               @Inject(MAT_DIALOG_DATA) public data: boolean) { }

  ngOnInit() {
  }

  onNoClick(): void {
    this.dialogRef.close(false);
  }

}
