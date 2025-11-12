import {Player} from "./Classes/Player.js";
import {Platform} from "./Classes/Platform.js";
import {setupEventListeners} from "./EventListeners.js";

export const canvas = document.querySelector("canvas");
export const ctx = canvas.getContext("2d");

canvas.width = 1280;
canvas.height = 720;

export const keys = {
    d: {pressed: false},
    a: {pressed: false},
    space: {pressed: false}
};

export const player = new Player({
    position: {x: 200, y: 300},
    imageSrc: "./Assets/Player",
});

const platforms = [
    new Platform({
        x: 200,
        y: 600,
        width: 400,
        height: 30,
        color: "maroon"
    }),
    new Platform({
        x: 600,
        y: 450,
        width: 400,
        height: 30,
    })
];

setupEventListeners(keys, player);

function animate() {
    requestAnimationFrame(animate);

    ctx.clearRect(0, 0, canvas.width, canvas.height);

    platforms.forEach(platform => platform.draw());

    player.draw();
    player.update(keys, platforms);
}

animate();