function sum(a: number, b: number): number {
    return a + b;
}

function defaultSum(a, b = 2) {
    return a + b;
}

function restSum(...numbers: number[]): number {
    //return numbers.reduce((a, b) => a + b, 0);
    let result: number = 0;
    for (const num in numbers) {
        result += numbers[num];
    }

    return result;
}

function optionalSum(a: number, b: number, c?: number): number {
    return a + b + (c || 0);
}

console.log(sum(10, 12));
console.log(defaultSum(10));
console.log(restSum(41, 52, 6, 1, 85, 32));
console.log(optionalSum(41, 52, 6));