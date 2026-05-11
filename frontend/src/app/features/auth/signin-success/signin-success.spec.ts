import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SigninSuccess } from './signin-success';

describe('SigninSuccess', () => {
  let component: SigninSuccess;
  let fixture: ComponentFixture<SigninSuccess>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SigninSuccess],
    }).compileComponents();

    fixture = TestBed.createComponent(SigninSuccess);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
