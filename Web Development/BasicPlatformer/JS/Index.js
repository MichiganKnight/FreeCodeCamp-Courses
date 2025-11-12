import {Player} from "./Classes/Player.js";
import {Platform} from "./Classes/Platform.js";
import {setupEventListeners} from "./EventListeners.js";

export const canvas = document.querySelector("canvas");
export const ctx = canvas.getContext("2d");

canvas.width = 1280;
canvas.height = 720;

export const level = {
    width: 5000,
    height: canvas.height
}

export const keys = {
    d: {pressed: false},
    a: {pressed: false}
};

export const player = new Player({
    position: {x: 200, y: 300},
    imageSrc: "./Assets/Player"
});

export const camera = {
    x: 0,
    y: 0
};

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

const backgroundImage = new Image();
backgroundImage.src = "./Assets/Level/Background/Blue.png";
let backgroundPattern = null;

backgroundImage.onload = () => {
    backgroundPattern = ctx.createPattern(backgroundImage, "repeat");
};

function drawBackground() {
    if (!backgroundPattern) return;

    const tileSize = 64;

    const offsetX = -camera.x % tileSize;
    const offsetY = -camera.y % tileSize;

    const startX = offsetX > 0 ? offsetX - tileSize : offsetX;
    const startY = offsetY > 0 ? offsetY - tileSize : offsetY;

    const tilesX = Math.ceil(canvas.width / tileSize) + 1;
    const tilesY = Math.ceil(canvas.height / tileSize) + 1;

    ctx.save();
    ctx.translate(startX, startY);
    ctx.fillStyle = backgroundPattern;

    for (let y = 0; y < tilesY; y++) {
        for (let x = 0; x < tilesX; x++) {
            ctx.fillRect(x * tileSize, y * tileSize, tileSize, tileSize);
        }
    }

    ctx.restore();
}

function animate() {
    requestAnimationFrame(animate);

    ctx.clearRect(0, 0, canvas.width, canvas.height);

    if (backgroundPattern) {
        drawBackground();
    } else {
        ctx.fillStyle = "lightblue";
        ctx.fillRect(0, 0, canvas.width, canvas.height);
    }

    platforms.forEach(platform => platform.draw());

    player.draw();
    player.update(keys, platforms);
}

player.image.onload = () => {
    animate();
};