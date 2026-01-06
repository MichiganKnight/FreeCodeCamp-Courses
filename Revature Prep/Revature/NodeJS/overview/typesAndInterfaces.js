var Person = /** @class */ (function () {
    function Person(firstName, lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }
    return Person;
}());
var Pet = /** @class */ (function () {
    function Pet(name, animal) {
        this.name = name;
        this.animal = animal;
    }
    return Pet;
}());
var person = new Person("Drew", "Fleming");
var pet = new Pet("Carson", "Dog");
doSomething(person);
doSomething(pet);
function doSomething(creature) {
    if (creature instanceof Person) {
        console.log("Hello ".concat(creature.firstName, " ").concat(creature.lastName, "!"));
    }
    else {
        console.log("Hello ".concat(creature.name, " the ").concat(creature.animal, "!"));
    }
}
