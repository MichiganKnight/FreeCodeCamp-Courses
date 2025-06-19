console.log("=== For Loops ===");

var myArray = [];

for (var i = 1; i < 6; i++) {
    myArray.push(i);
}

console.log(myArray);

var ourArray = [];
var myArray = [];

for (var i = 0; i < 10; i += 2) {
    ourArray.push(i);
}

for (var i = 1; i < 10; i += 2) {
    myArray.push(i);
}

console.log(ourArray);
console.log(myArray);

var ourArray = [];
var myArray = [];

for (var i = 10; i > 0; i -= 2) {
    ourArray.push(i);
}

for (var i = 9; i > 0; i -= 2) {
    myArray.push(i);
}

console.log(ourArray);
console.log(myArray);

console.log("\n=== Iterate Through Array ===");

var myArray = [2, 3, 4, 5, 6];
var total = 0;

for (var i = 0; i < myArray.length; i++) {
    total += myArray[i];
}

console.log(total);

console.log("\n=== Nested For Loop ===");

function multiplyAll(arr) {
    var product = 1;

    for (var i = 0; i < arr.length; i++) {
        for (var j = 0; j < arr[i].length; j++) {
            product *= arr[i][j];
        }
    }

    return product;
}

var product = multiplyAll([[1, 2], [3, 4], [5, 6, 7]]);

console.log(product);

console.log("\n=== Profile Lookup ===");

var contacts = [
    {
        "FirstName": "Akira",
        "LastName": "Laine",
        "Number": "0543236543",
        "Likes": ["Pizza", "Coding", "Brownie Points"]
    },
    {
        "FirstName": "Harry",
        "LastName": "Potter",
        "Number": "0994372684",
        "Likes": ["Hogwarts", "Magic", "Hagrid"]
    },
    {
        "FirstName": "Sherlock",
        "LastName": "Holmes",
        "Number": "0487345643",
        "Likes": ["Intriguing Cases", "Violin"]
    },
    {
        "FirstName": "Kristian",
        "LastName": "Vos",
        "Number": "Unknown",
        "Likes": ["JavaScript", "Gaming", "Foxes"]
    }
];

function lookUpProfile(name, prop) {
    for (var i = 0; i < contacts.length; i++) {
        if (contacts[i].FirstName === name) {
            return contacts[i][prop] || "No Such Property";
        }
    }

    return "No Such Contact";
}

var data = lookUpProfile("Akira", "Likes");
var data = lookUpProfile("Sherlock", "LastName");
var data = lookUpProfile("Akira", "Hello");

console.log(data);