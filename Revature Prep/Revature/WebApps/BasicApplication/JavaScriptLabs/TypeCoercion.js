let a1 = document.getElementById("a1");
let a2 = document.getElementById("a2");
let a3 = document.getElementById("a3");
let a4 = document.getElementById("a4");
let a5 = document.getElementById("a5");
let a6 = document.getElementById("a6");
let a7 = document.getElementById("a7");
let a8 = document.getElementById("a8");

a1.innerText = doubleEquals(5, 6);
a2.innerText = doubleEquals(5, 5);
a3.innerText = doubleEquals(5, "5");
a4.innerText = doubleEquals("5", "5");
a5.innerText = tripleEquals(5, 6);
a6.innerText = tripleEquals(5, 5);
a7.innerText = tripleEquals(5, "5");
a8.innerText = tripleEquals("5", "5");

function doubleEquals(a, b) {
    return a == b;
}

function tripleEquals(a, b) {
    return a === b;
}

let a9 = document.getElementById("a9");
let a10 = document.getElementById("a10");
let a11 = document.getElementById("a11");
let a12 = document.getElementById("a12");

a9.innerText = toString(5) + 6;
a10.innerText = toNumber("5") + 6;
a11.innerText = toBoolean(5) + 6;
a12.innerText = toBoolean(null) + 6;

function toString(a) {
    return a.toString();
}

function toNumber(a) {
    return Number(a);
}

function toBoolean(a) {
    return Boolean(a);
}