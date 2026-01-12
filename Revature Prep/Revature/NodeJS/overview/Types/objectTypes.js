function greet(person) {
    return "Hello ".concat(person.firstName, " ").concat(person.lastName, "!");
}
console.log(greet({ firstName: "Jane", lastName: "User" }));
