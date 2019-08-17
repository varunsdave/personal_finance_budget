import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ConfirmCategorizationActionDialogComponent } from './confirm-categorization-action-dialog.component';

describe('ConfirmCategorizationActionDialogComponent', () => {
  let component: ConfirmCategorizationActionDialogComponent;
  let fixture: ComponentFixture<ConfirmCategorizationActionDialogComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ConfirmCategorizationActionDialogComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ConfirmCategorizationActionDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
