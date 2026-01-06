let content = document.getElementById("content");
let button = document.getElementById("button");

button.addEventListener("click", getFact);

async function getFact() {
    let HTTPResponse = await fetch("https://catfact.ninja/fact");

    let responseObject = await HTTPResponse.json();

    content.innerText = responseObject.fact;

    console.log(responseObject);
}