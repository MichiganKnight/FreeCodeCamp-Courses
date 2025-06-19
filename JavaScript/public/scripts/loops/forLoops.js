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