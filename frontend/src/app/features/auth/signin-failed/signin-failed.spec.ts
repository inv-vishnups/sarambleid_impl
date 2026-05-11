import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SigninFailed } from './signin-failed';

describe('SigninFailed', () => {
  let component: SigninFailed;
  let fixture: ComponentFixture<SigninFailed>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SigninFailed],
    }).compileComponents();

    fixture = TestBed.createComponent(SigninFailed);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
