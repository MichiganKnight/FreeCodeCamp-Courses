// @ts-ignore
class Employee {
    readonly empCode: number;
    empName: string;

    constructor(empCode: number, empName: string) {
        this.empCode = empCode;
        this.empName = empName;
    }
}

// @ts-ignore
let emp = new Employee(10, "Drew");
emp.empCode = 20;
// @ts-ignore
emp.empName = "John";