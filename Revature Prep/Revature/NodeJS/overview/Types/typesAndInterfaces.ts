class Person {
    firstName: string;
    lastName: string;

    constructor(firstName: string, lastName: string) {
        this.firstName = firstName;
        this.lastName = lastName;
    }
}

class Pet {
    name: string;
    animal: string;

    constructor(name: string, animal: string) {
        this.name = name;
        this.animal = animal;
    }
}

let person: Person = new Person("Drew", "Fleming");
let pet: Pet = new Pet("Carson", "Dog");

doSomething(person);
doSomething(pet);

function doSomething(creature: Person | Pet) {
    if (creature instanceof Person) {
        console.log(`Hello ${creature.firstName} ${creature.lastName}!`);
    } else {
        console.log(`Hello ${creature.name} the ${creature.animal}!`);
    }
}


