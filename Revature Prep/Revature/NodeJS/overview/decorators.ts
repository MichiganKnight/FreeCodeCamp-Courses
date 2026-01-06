/*class Animal {
    private species: string;
    private name: string;
    private food: string;
    private age: number;

    constructor(species: string, name: string, food: string, age: number) {
        this.species = species;
        this.name = name;
        this.food = food;
        this.age = age;
    }

    get getSpecies(): string {
        return this.species;
    }

    get getName(): string {
        return this.name;
    }

    get getFood(): string {
        return this.food;
    }

    get getAge(): number {
        return this.age;
    }

    set setFood(newFood: string) {
        this.food = newFood;
    }

    set setAge(newAge: number) {
        this.age = newAge;
    }
}

let animal = new Animal("Dog", "Scooby", "Scooby Snacks", 5);

console.log(animal);

animal.setAge = 6;
animal.setFood = "More Scooby Snacks";

console.log(animal);*/

class Animal {
    @required
    private readonly species: string;

    @required
    private readonly name: string;

    private food: string;
    private age: number;

    constructor(species: string, name: string, food: string, age: number) {
        this.species = species;
        this.name = name;
        this.food = food;
        this.age = age;
    }

    get getSpecies(): string {
        return this.species;
    }

    get getName(): string {
        return this.name;
    }

    get getFood(): string {
        return this.food;
    }

    get getAge(): number {
        return this.age;
    }

    set setFood(newFood: string) {
        this.food = newFood;
    }

    set setAge(newAge: number) {
        this.age = newAge;
    }
}

let animal = new Animal("", "","Scooby Snacks", 5);

function required(target: any, key: string) {
    let currentValue = target[key];

    Object.defineProperty(target, key, {
        get: () => currentValue,

        set: (value: any) => {
            if (!value) {
                throw new Error(`${key} is a Required Field`);
            }

            currentValue = value;
        }
    });
}