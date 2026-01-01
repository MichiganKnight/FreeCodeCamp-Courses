let button1 = document.getElementById("button1");
let button2 = document.getElementById("button2");

let reset = document.getElementById("reset");

let text1 = document.getElementById("text1");
let text2 = document.getElementById("text2");
let text3 = document.getElementById("text3");

resetText();

reset.addEventListener("click", resetText);

button1.addEventListener("click", updateTextOnButton1Click);
button2.addEventListener("click", updateTextOnButton2Click);
button1.addEventListener("click", updateTextOnEitherButtonClick);
button2.addEventListener("click", updateTextOnEitherButtonClick);

function resetText() {
    text1.innerText = "this text will change when button1 is clicked";
    text2.innerText = "this text will change when either button1 or button2 is clicked";
    text3.innerText = "this text will change when button2 is clicked";
}

function updateTextOnButton1Click() {
    text1.innerText = "button1 was clicked!";
}

function updateTextOnEitherButtonClick() {
    text2.innerText = "either button1 or button2 was clicked!";
}

function updateTextOnButton2Click() {
    text3.innerText = "button2 was clicked!";
}