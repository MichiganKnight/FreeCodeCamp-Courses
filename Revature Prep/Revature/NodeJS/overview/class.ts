class Person {
    name: string;

    constructor(name: string) {
        this.name = name;
    }
}

class Employee extends Person {
    empCode: number;

    constructor(empCode: number, name: string) {
        super(name);
        this.empCode = empCode;
    }

    displayName(): void {
        console.log(`Employee Name: ${this.name} - Employee Code: ${this.empCode}`);
    }

    getSalary(): number {
        return Math.round(Math.random() * 10000);
    }
}

let emp = new Employee(1, "Drew Fleming");
emp.displayName();

console.log(`Salary: $${emp.getSalary()}.00`);