console.log("=== Variables ===");

// In-line Comment

/*
    Multi-Line Comment
*/

/* Data Types:
    undefined, null, boolean, string, symbol, number, and object
*/

// Used Throughout Program
var myName = "Drew";

console.log(myName);

// Used in Scope
let ourName = "michiganknight";

// Variable Cannot Change
const pi = 3.14;

var a;
var b = 2;

a = 7;

b = a;

console.log(`Variable A: ${a}\nVariable B: ${b}`);

console.log("\n=== ParseInt() Function ===");

function convertToInteger(str) {
    return parseInt(str);
}

console.log(convertToInteger("56"));

function convertBase2(str) {
    return parseInt(str, 2);
}

console.log(convertBase2("10011"));