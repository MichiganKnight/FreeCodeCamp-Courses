import {Component, OnInit, signal} from '@angular/core';
import { RouterOutlet } from '@angular/router';
import {EmployeeService} from '../employee.service';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet],
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class App implements OnInit {
  constructor(private employeeService : EmployeeService) {
    new_employee = {
      "id": 18,
      "name": "Grace",
      "age": 22
    };
    ngOnInit() {
      this.employeeService.CreateEmployee(this.new_employee)
        .subscribe(data => {
          console.log("Post Request for Creating New Employee");
          console.log("id: " + data.id + " name: " + data.name + " age: " + data.age + "");
        })
    }
  }

  private ngOnInit() {

  }
}
