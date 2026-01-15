import { Component } from '@angular/core';

@Component({
  selector: 'app-app-component',
  templateUrl: './app-component.html',
  styleUrl: './app-component.css',
})
export class AppComponent {
  color="white";
  title="Sample";
  highlightColor(newColor: string): void {
    this.color = newColor;
  }
}
