function greeter(person) {
    return "Hello ".concat(person, "!");
}
var user = "Jane User";
document.body.textContent = greeter(user);
