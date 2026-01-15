import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class CountService {

  num:number = 0;
  constructor() { }

  getNum():number {
    return this.num;
  }
  incrementNum():void {
    this.num++;
  }
  decrementNum():void {
    this.num--;
  }
}
