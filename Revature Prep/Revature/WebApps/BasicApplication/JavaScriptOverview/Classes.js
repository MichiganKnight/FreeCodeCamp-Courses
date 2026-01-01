class User {
    constructor(name) {
        this.name = name;
    }

    sayHi() {
        console.log(this.name);
    }
}

let user = new User("John");
user.sayHi();

class Car {
    constructor(make, model) {
        this.make = make;
        this.model = model;
    }

    drive() {
        console.log(`${this.make} ${this.model} is driving!`);
    }
}

let fordMustang = new Car("Ford", "Mustang");
let toyotaCorolla = new Car("Toyota", "Corolla");
let acuraMDX = new Car("Acura", "MDX");

fordMustang.drive();
toyotaCorolla.drive();
acuraMDX.drive();