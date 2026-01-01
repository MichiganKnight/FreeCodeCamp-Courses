class Person {
    name;

    constructor(name) {
        this.name = name;
    }

    talk() {
        console.log("Hello World!");
    }
}

class Student extends Person {
    university;

    constructor(name, university) {
        super(name);
        this.university = university;
    }

    talk() {
        console.log(`Hello World! I am a student at ${this.university}`);
    }
}

let person = new Person("John");
person.talk();

let student = new Student("John", "University of California, Berkeley");
student.talk();