console.log("=== Class Syntax ===");

class SpaceShuttle {
    constructor(targetPlanet) {
        this.targetPlanet = targetPlanet;
    }
}

var zues = new SpaceShuttle('Jupiter');

console.log(zues.targetPlanet);

function makeClass() {
    class Vegetable {
        constructor(name) {
            this.name = name;
        }
    }

    return Vegetable;
}

const Vegetable = makeClass();
const carrot = new Vegetable('Carrot');

console.log(carrot.name);