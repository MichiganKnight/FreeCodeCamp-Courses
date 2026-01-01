let content = document.getElementById("content");
let input = document.getElementById("input");
let pushButton = document.getElementById("pushButton");
let popButton = document.getElementById("popButton");
let length = document.getElementById("length");

let arr = [];

content.innerText = arr;
length.innerText = 0;

pushButton.onclick = updatePush;
popButton.onclick = updatePop;

function updatePush() {
    arrPush(input.value);
    content.innerText = arr;
    length.innerText = arrLength();
}

function updatePop() {
    arrPop();
    content.innerText = arr;
    length.innerText = arrLength();
}

function arrPush(item) {
    arr.push(item);
}

function arrPop() {
    arr.pop();
}

function arrLength() {
    return arr.length;
}