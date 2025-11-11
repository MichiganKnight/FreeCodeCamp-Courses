import {Player} from "./Classes/Player.js";
import {Platform} from "./Classes/Platform.js";

import {setupEventListeners} from "./EventListeners.js";

export const canvas = document.querySelector("canvas");
export const ctx = canvas.getContext("2d");

canvas.width = 1280;
canvas.height = 720;

export const player = new Player();
export const keys = {
    a: {
        pressed: false
    },
    d: {
        pressed: false
    }
};

const platforms = [
    new Platform({
        x: 200,
        y: 600
    }),
    new Platform({
        x: 400,
        y: 450,
        color: "green"
    })
];

setupEventListeners();

function animate() {
    requestAnimationFrame(animate);

    ctx.clearRect(0, 0, canvas.width, canvas.height);

    player.update();

    platforms.forEach(platform => platform.draw());

    // Player Movement
    if (keys.d.pressed && player.position.x < 400) {
        player.velocity.x = 5;
    } else if (keys.a.pressed && player.position.x > 0) {
        player.velocity.x = -5;
    } else {
        player.velocity.x = 0;

        if (keys.d.pressed) {
            platforms.forEach(platform => platform.position.x -= 5);
        } else if (keys.a.pressed) {
            platforms.forEach(platform => platform.position.x += 5);
        }
    }

    // Platform Collision
    platforms.forEach(platform => {
        if (player.position.y + player.height <= platform.position.y &&
            player.position.y + player.height + player.velocity.y >= platform.position.y &&
            player.position.x + player.width >= platform.position.x &&
            player.position.x <= platform.position.x + platform.width) {
            player.velocity.y = 0;
        }
    });
}

animate();