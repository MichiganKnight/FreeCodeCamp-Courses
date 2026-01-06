function sum(a, b) {
    return a + b;
}
function defaultSum(a, b) {
    if (b === void 0) { b = 2; }
    return a + b;
}
function restSum() {
    var numbers = [];
    for (var _i = 0; _i < arguments.length; _i++) {
        numbers[_i] = arguments[_i];
    }
    //return numbers.reduce((a, b) => a + b, 0);
    var result = 0;
    for (var num in numbers) {
        result += numbers[num];
    }
    return result;
}
console.log(sum(10, 12));
console.log(defaultSum(10));
console.log(restSum(41, 52, 6, 1, 85, 32));
