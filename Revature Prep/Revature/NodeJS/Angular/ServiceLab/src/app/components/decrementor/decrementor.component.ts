import {Component, EventEmitter, OnInit, Output} from '@angular/core';
import {CountService} from "../../services/count.service";

@Component({
  selector: 'app-decrementor',
  templateUrl: './decrementor.component.html',
  styleUrls: ['./decrementor.component.css']
})
export class DecrementorComponent implements OnInit {
  @Output()
  decrementEvent : EventEmitter<void> = new EventEmitter<void>();

  /**
   * The CountService has been injected into this component's Constructor, making the Service
   * singleton countService available throughout the component, as you can see in emitIncrementEvent().
   * @param countService
   */
  constructor(private countService : CountService) { }

  ngOnInit(): void {

  }

  emitDecrementEvent() {
    this.countService.decrementNum();
    this.decrementEvent.emit();
  }
}
