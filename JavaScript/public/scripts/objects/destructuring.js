console.log("=== Destructuring Assignment ===");

var voxel = { x: 3.6, y: 7.4, z: 6.54 };

var x = voxel.x;
var y = voxel.y;
var z = voxel.z;

const { x: a, y: b, z: c } = voxel;

const AVG_TEMPERATURES = {
    Today: 77.5,
    Tomorrow: 79
};

function getTempOfTmrw(avgTemperatures) {
    "use strict";

    const { Tomorrow: tempOfTomorrow } = avgTemperatures;

    return tempOfTomorrow;
}

console.log(getTempOfTmrw(AVG_TEMPERATURES));

const LOCAL_FORECAST = {
    today: { min: 72, max: 83 },
    tomorrow: { min: 73.3, max: 84.6 }
};

function getMaxOfTmrw(forecast) {
    "use strict";

    const { tomorrow: { max: maxOfTomorrow } } = forecast;

    return maxOfTomorrow;
}

console.log(getMaxOfTmrw(LOCAL_FORECAST));

const [otherZ, otherX, , otherY] = [1, 2, 3, 4, 5, 6];
console.log(otherZ, otherX, otherY);

let otherA = 8, otherB = 6;

(() => {
    "use strict";

    [otherA, otherB] = [otherB, otherA];
})();

console.log(otherA);
console.log(otherB);

const source = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10];

function removeFirstTwo(list) {
    const [, , ...arr] = list;

    return arr;
}

const arr = removeFirstTwo(source);

console.log(arr);
console.log(source);

const stats = {
    max: 56.78,
    standard_deviation: 4.34,
    median: 34.54,
    mode: 23.87,
    min: -0.75,
    average: 35.85
};

const half = (function () {
    return function half({ max, min }) {
        return (max + min) / 2.0;
    };
})();

console.log(stats);
console.log(half(stats));