import { Component, signal } from '@angular/core';
import { Child } from './child/child';

@Component({
  selector: 'app-root',
  imports: [
    Child
  ],
  template:
    `<h2>Event Emitters</h2>
    <p>At AppComponent - Count = {{count}}</p>
    <app-child [count]="count" (change)="countChange($event)"></app-child>`
})
export class App {
  count = 9;
  countChange(event: number) {
    this.count = event;
  }
}
