import { Player } from "./Classes/Player.js";
import { setupEventListeners } from "./EventListeners.js";
import { buildPlatformsFromLayer, drawTileLayer, getLayerByName, loadJSON, loadTilesets } from "./Utils.js";

export const canvas = document.querySelector("canvas");
export const ctx = canvas.getContext("2d");

canvas.width = 1280;
canvas.height = 720;

export const level = {
    width: canvas.width,
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

let currentMap = null;
let tilesets = [];
let platforms = [];

let bgLayer = null;
let terrainLayer = null;
let platformsLayer = null;
let fgLayer = null;

async function loadLevel() {
    currentMap = await loadJSON("./Data/Levels/EditorLevel.json");

    level.width = currentMap.width * currentMap.tilewidth;
    level.height = currentMap.height * currentMap.tileheight;

    tilesets = await loadTilesets();

    bgLayer = null;
    platformsLayer = null;
    fgLayer = null;

    terrainLayer = getLayerByName(currentMap, "Terrain");

    platforms = buildPlatformsFromLayer(currentMap, "Terrain", {
        mode: "full",
        oneWay: false
    });

    const objectsLayer = getLayerByName(currentMap, "Objects");
    if (objectsLayer && objectsLayer.type === "objectgroup") {
        const playerObj = objectsLayer.objects.find(obj => obj.name === "Player");
        if (playerObj) {
            player.position.x = playerObj.x;
            player.position.y = playerObj.y - playerObj.height;
        }
    }
}

function drawMap() {
    if (!currentMap || tilesets.length === 0) return;

    const tileWidth = currentMap.tilewidth;
    const tileHeight = currentMap.tileheight;

    drawTileLayer(ctx, camera, terrainLayer, tilesets, tileWidth, tileHeight);
}

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

    drawMap();

    //platforms.forEach(platform => platform.draw());

    player.draw();
    player.update(keys, platforms);
}

async function start() {
    await loadLevel();
    setupEventListeners(keys, player);
    animate();
}

start().then(r => console.log("Game Started"));