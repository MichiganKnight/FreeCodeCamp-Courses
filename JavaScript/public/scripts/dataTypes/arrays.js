console.log("=== Arrays ===");

var myArray = ["Drew", 1];
console.log(myArray);

var myArray = [["Bulls", 23], ["White Sox", 4]];
console.log(myArray);

var myArray = [50, 60, 70];
var myData = myArray[0];

console.log(myData);

var myArray = [18, 64, 99];
myArray[0] = 45;

console.log(myArray);

console.log("\n=== Multi-Dimensional Arrays ===");

var myArray = [[1, 2, 3], [4, 5, 6], [7, 8, 9], [[10, 11, 12], 13, 14]];
var myData = myArray[2][1];

console.log(myData);

console.log("\n=== Array Manipulation ===");

var myArray = [["John", 23], ["Cat", 2]];
myArray.push(["Dog", 3]);

console.log(myArray);

var myArray = [["John", 23], ["Cat", 2]];
var removedFromOurArray = myArray.pop();

console.log(myArray);

var myArray = [["John", 23], ["Dog", 3]];
var removedFromOurArray = myArray.shift();

console.log(myArray);

var myArray = [["John", 23], ["Dog", 3]];
myArray.unshift("Happy");

console.log(myArray);

console.log("\n=== Shopping List ===");

var myList = [["Cereal", 3], ["Milk", 2], ["Bananas", 3], ["Juice", 2], ["Eggs", 12]];
console.log(myList);