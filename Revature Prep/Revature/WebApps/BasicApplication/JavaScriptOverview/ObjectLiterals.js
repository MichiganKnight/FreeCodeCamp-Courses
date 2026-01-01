let person = {
    firstName: "John",
    lastName: "Smith",
    age: 30,
    greet: function() {
        return `Hello - Name: ${this.firstName} ${this.lastName} | Age: ${this.age}`;
    }
};

console.log(person.firstName);
console.log(person["lastName"]);
console.log(person.greet());

function Person(firstName, lastName, age) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.age = age;

    this.greet = function() {
        return `Hello - Name: ${this.firstName} ${this.lastName} | Age: ${this.age}`;
    };
}

let johnSmith = new Person("John", "Smith", 30);

console.log(johnSmith.firstName);
console.log(johnSmith["lastName"]);
console.log(johnSmith.greet());