console.log("=== Random Numbers ===");

function randomFraction() {
    return Math.random();
}

console.log(randomFraction());

var randomNumberBetween0and19 = Math.floor(Math.random() * 20);

function randomWholeNumb() {
    return Math.floor(Math.random() * 10);
}

console.log(randomWholeNumb());

function randomRange(myMin, myMax) {
    return Math.floor(Math.random() * (myMax - myMin + 1)) + myMin;
}

var myRandom = randomRange(5, 15);

console.log(myRandom);