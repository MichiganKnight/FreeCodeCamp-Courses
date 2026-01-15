import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LifecycleComponent } from './lifecycle.component';

describe('LifecycleComponent', () => {
  let component: LifecycleComponent;
  let fixture: ComponentFixture<LifecycleComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ LifecycleComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(LifecycleComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should update time when component is mounted', () => {
    const tolerance = 1000; // 1 second
    const expected = Date.now();
    fixture.detectChanges();

    expect(component.time).toBeGreaterThanOrEqual(expected - tolerance);
    expect(component.time).toBeLessThanOrEqual(expected + tolerance);
  })

  it('should trigger alert when component is unmounted', () => {
    // Spy on the alert function
    const alertSpy = spyOn(window, 'alert');

    // Manually call ngOnDestroy to simulate component unmounting
    component.ngOnDestroy();

    // Assert that the alert function was called
    expect(alertSpy).toHaveBeenCalled();
  });
});
