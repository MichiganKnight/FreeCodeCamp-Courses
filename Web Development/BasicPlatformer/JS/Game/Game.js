import { Player } from "./Classes/Player.js";
import { setupEventListeners } from "./EventListeners.js";
import {
    loadTilesetsFromMap, fetchMapJson, normalizeLayerData
} from "./Utils.js";
import { drawTileLayer } from "../Editor/Renderer.js";

export const canvas = document.querySelector("canvas");
export const ctx = canvas.getContext("2d");

canvas.width = 1280;
canvas.height = 720;

export const keys = {d: {pressed: false}, a: {pressed: false}};

export const player = new Player({
    position: {x: 200, y: 300},
    imageSrc: "./Assets/Player"
});

export const camera = {x: 0, y: 0};

let currentMap = null;
let tilesets = [];
let platforms = [];
let terrainLayer = null;

export const level = {
    width: 1280,
    height: 720
};

async function loadLevel() {
    /* Usage example (in `JS/Game/Game.js`):
   const map = await fetchMapJson('/Assets/Level/yourmap.json');
   const tilesets = loadTilesetsFromMap(map);
   const groundLayer = map.layers.find(l => l.name === 'Ground');
   const data = normalizeLayerData(groundLayer);
*/

    const map = await fetchMapJson("./Data/Levels/EditorLevel.json");
    const tilesets = loadTilesetsFromMap(map);
    const groundLayer = map.layers.find(l => l.name === 'Terrain');
    const data = normalizeLayerData(groundLayer);

    currentMap = {
        width: map.width,
        height: map.height,
        tilewidth: map.tilewidth,
        tileheight: map.tileheight,
        layers: [{name: "Terrain", data}]
    };

    console.log(currentMap);

    //currentMap = await loadJSON("./Data/Levels/EditorLevel.json");

    //let test = importEditorLevel(loadJSON("./Data/Levels/EditorLevel.json"));

    //level.width = currentMap.width * currentMap.tilewidth;
    //level.height = currentMap.height * currentMap.tileheight;

    //level.width = test.width * currentMap.tilewidth;
    //level.height = test.height * currentMap.tileheight;

    //tilesets = await loadTilesetsFromMap(test);

    // Load tilesets dynamically from JSON
    //tilesets = await loadTilesetsFromMap(currentMap);

    // Resize game world
    //canvas.width = Math.min(currentMap.width * currentMap.tilewidth, 1280);
    //canvas.height = Math.min(currentMap.height * currentMap.tileheight, 720);

    //terrainLayer = getLayerByName(currentMap, "Terrain");

    //platforms = buildPlatformsFromLayer(currentMap, "Terrain", {mode: "full"});

    //const objectsLayer = getLayerByName(currentMap, "Objects");
    /*if (objectsLayer) {
        const playerObj = objectsLayer.objects.find(obj => obj.name === "Player");
        if (playerObj) {
            player.position.x = playerObj.x;
            player.position.y = playerObj.y - playerObj.height;
        }
    }

    console.log("Level Loaded:", currentMap);
    console.log("Tilesets:", tilesets);
    console.log("Platforms:", platforms);*/
}

function drawMap() {
    if (!currentMap || tilesets.length === 0) return;
    drawTileLayer(ctx, camera, terrainLayer, tilesets, currentMap.tilewidth, currentMap.tileheight);
}

// Background
const backgroundImage = new Image();
backgroundImage.src = "./Assets/Level/Background/Blue.png";
let backgroundPattern = null;
backgroundImage.onload = () => backgroundPattern = ctx.createPattern(backgroundImage, "repeat");

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

    backgroundPattern ? drawBackground() : ctx.fillRect(0, 0, canvas.width, canvas.height);

    drawMap();

    // Draw platforms if you want to debug (optional)
    // platforms.forEach(p => p.draw());

    player.draw();
    player.update(keys, platforms);
}

async function start() {
    await loadLevel();
    setupEventListeners(keys, player);
    animate();
}

start().then(() => console.log("Game Started"));