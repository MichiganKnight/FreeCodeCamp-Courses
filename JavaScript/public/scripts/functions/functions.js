console.log("=== Functions ===");

function myReusableFunction() {
    console.log("Hello World!");
}

myReusableFunction();
myReusableFunction();
myReusableFunction();

function myFunctionWithArgs(a, b) {
    console.log(a + b);
}

myFunctionWithArgs(10, 5);
myFunctionWithArgs(5, 2);

console.log("\n=== Global Scope ===");

var myGlobal = 10;

function fun1() {
    oopsGlobal = 5;
}

function fun2() {
    var output = "";

    if (typeof myGlobal != "undefined") {
        output += `myGlobal: ${myGlobal}`;
    }

    if (typeof oopsGlobal != "undefined") {
        output += `oopsGlobal: ${oopsGlobal}`;
    }

    console.log(output);
}

fun1();
fun2();

console.log("\n === Local Scope ===");

function myLocalScope() {
    var myVar = 5;

    console.log(myVar);
}

myLocalScope();

console.log("\n=== Global vs Local Scope ===");

var outerWear = "T-Shirt";

function myOutfit() {
    var outerWear = "Sweater";

    return outerWear;
}

console.log(myOutfit());
console.log(outerWear);

console.log("\n=== Return a Value From a Function ===");

function minusSeven(num) {
    return num - 7;
}

function timesFive(num) {
    return num * 5;
}

console.log(minusSeven(10));
console.log(timesFive(5));

console.log("\n=== Assignment With Return ===");

var processed = 0;

function processArg(num) {
    return (num + 3) / 5;
}

processed = processArg(7);

console.log(processed);

console.log("\n=== Stand In Line ===");

function nextInLine(arr, item) {
    arr.push(item);

    return arr.shift();
}

var testArr = [1, 2, 3, 4, 5];

console.log(`Before: ${JSON.stringify(testArr)}`);
console.log(nextInLine(testArr, 6));
console.log(`After: ${JSON.stringify(testArr)}`);