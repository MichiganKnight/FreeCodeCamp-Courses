let input = document.getElementById("input");
let button = document.getElementById("button");
let result = document.getElementById("result");

button.onclick = getSum;

function getSum() {
    let val = parseInt(input.value);
    let sum = 0;

    for (let i = 0; i < val; i++) {
        sum += i;
    }

    result.innerText = sum;
}