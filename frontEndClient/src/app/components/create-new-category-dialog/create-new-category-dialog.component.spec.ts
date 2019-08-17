import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateNewCategoryDialogComponent } from './create-new-category-dialog.component';

describe('CreateNewCategoryDialogComponent', () => {
  let component: CreateNewCategoryDialogComponent;
  let fixture: ComponentFixture<CreateNewCategoryDialogComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CreateNewCategoryDialogComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CreateNewCategoryDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
