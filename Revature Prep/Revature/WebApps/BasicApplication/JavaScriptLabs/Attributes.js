let image = document.getElementById("image");
let input = document.getElementById("input");
let button = document.getElementById("button");

button.onclick = setImageSrc;

function setImageSrc() {
    image.src = input.value;
}