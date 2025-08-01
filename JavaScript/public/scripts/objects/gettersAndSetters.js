console.log("=== Getters and Setters ===");

class Book {
    constructor(author) {
        this._author = author;
    }

    get writer() {
        return this._author;
    }

    set writer(updatedAuthor) {
        this._author = updatedAuthor;
    }
}

function makeClass() {
    class Thermostat {
        constructor(temp) {
            // Private Variable
            this._temp = 5 / 9 * (temp - 32);
        }

        get temperature() {
            return this._temp;
        }

        set temperature(updatedTemp) {
            this._temp = updatedTemp;
        }
    }

    return Thermostat;
}

const Thermostat = makeClass();
const thermos = new Thermostat(76);

let temp = thermos.temperature;
thermos.temperature = 26;
temp = thermos.temperature;

console.log(temp);