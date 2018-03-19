import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AccountValidateComponent } from './account-validate.component';

describe('AccountValidateComponent', () => {
  let component: AccountValidateComponent;
  let fixture: ComponentFixture<AccountValidateComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AccountValidateComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AccountValidateComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
