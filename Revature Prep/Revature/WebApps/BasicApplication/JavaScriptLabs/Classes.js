let container = document.getElementById("container");
let onButton = document.getElementById("onBulb");
let offButton = document.getElementById("offBulb");
let counter = 0;

onButton.onclick = () => {
    createBulb(true);
}

offButton.onclick = () => {
    createBulb(false);
}

function createBulb(state) {
    counter++;

    let bulb = new Lightbulb(state);
    let title = document.createElement("h2");
    title.innerText = "Lightbulb " + counter;

    let text = document.createElement('p');
    text.innerText = bulb.getDescription();

    container.appendChild(title);
    container.appendChild(text);
}

class Lightbulb {
    constructor(state) {
        this.state = state;
    }

    getState() {
        return this.state;
    }

    setState(state) {
        this.state = state;
    }

    getDescription() {
        return this.state ? "On" : "Off";
    }
}