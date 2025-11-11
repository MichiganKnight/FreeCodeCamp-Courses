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

const platform = new Platform();

setupEventListeners();

function animate() {
    requestAnimationFrame(animate);

    ctx.clearRect(0, 0, canvas.width, canvas.height);

    player.update();
    platform.draw();

    if (keys.a.pressed) {
        player.velocity.x = -5;
    } else if (keys.d.pressed) {
        player.velocity.x = 5;
    } else {
        player.velocity.x = 0;
    }

    // Platform Collision
    if (player.position.y + player.height <= platform.position.y &&
        player.position.y + player.height + player.velocity.y >= platform.position.y &&
        player.position.x + player.width >= platform.position.x &&
        player.position.x <= platform.position.x + platform.width) {
        player.velocity.y = 0;
    }
}

animate();