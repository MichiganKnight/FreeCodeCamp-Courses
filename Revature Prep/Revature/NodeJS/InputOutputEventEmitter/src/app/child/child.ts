import {Component, EventEmitter, Input, Output} from '@angular/core';

@Component({
  selector: 'app-child',
  template:
    `<p>Click to Increment Count:</p>
    <button (click)="increment()">Increment Count</button><br>
    <p>Click to Decrement Count:</p>
    <button (click)="decrement()">Decrement Count</button><br>
    `
})
export class Child {
  @Input()
  count: number = 0;

  @Output()
  change: EventEmitter<number> = new EventEmitter();

  increment() {
    this.count++;
    this.change.emit(this.count);
    console.log(`Incrementing Count: ${this.count}`);
  }

  decrement() {
    this.count--;
    this.change.emit(this.count);
    console.log(`Decrementing Count: ${this.count}`);
  }
}
