let button = document.getElementById("button");
let input = document.getElementById("input");
let list = document.getElementById("list");

button.onclick = addItem;

function addItem() {
    let item = document.createElement("li");
    item.innerText = input.value;
    list.appendChild(item);
}