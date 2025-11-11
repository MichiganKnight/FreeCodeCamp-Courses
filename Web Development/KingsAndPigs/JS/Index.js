import {Player} from "./Classes/Player.js";
import {Sprite} from "./Classes/Sprite.js";

import {setupEventListeners} from "./EventListeners.js";
import {collisionsLevel1} from "./Data/Collisions.js";
import {parse2D, createObjectsFrom2D} from './Utils.js';

export const canvas = document.querySelector('canvas');
export const ctx = canvas.getContext('2d');

canvas.width = 64 * 16;
canvas.height = 64 * 9;

export const keys = {
    d: {
        pressed: false
    },
    a: {
        pressed: false
    },
    space: {
        pressed: false
    }
}

export const collisionBlocks = createObjectsFrom2D(parse2D(collisionsLevel1, 16));
const startingPosition = {
    x: 200,
    y: 300
};

export const player = new Player({
    position: startingPosition,
    collisionBlocks,
    imageSrc: './Assets/King/IdleRight.png',
    frameRate: 11,
    frameBuffer: 10
});

setupEventListeners(keys, player);

const backgroundLevel1 = new Sprite({
    position: {
        x: 0,
        y: 0
    },
    imageSrc: './Assets/Background/BackgroundLevel1.png'
});

function animate() {
    window.requestAnimationFrame(animate);

    backgroundLevel1.draw();
    collisionBlocks.forEach(collisionBlock => {
        collisionBlock.draw();
    });

    player.draw();
    player.update(keys);
}

animate();