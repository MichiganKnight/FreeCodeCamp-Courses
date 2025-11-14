import { Platform } from "./Classes/Platform.js";

export async function loadJSON(url) {
    const res = await fetch(url);
    if (!res.ok) {
        throw new Error(`Failed to Load JSON: ${url}: ${res.statusText}`);
    }
    return await res.json();
}

export function loadImage(src) {
    return new Promise((resolve, reject) => {
        const img = new Image();
        img.src = src;
        img.onload = () => resolve(img);
        img.onerror = (err) => reject(err);
    });
}

const TILESETS = [
    {
        name: "Outside",
        firstgid: 1,
        imageSrc: "./Assets/Level/Tilesets/Outside.png",
        tileWidth: 64,
        tileHeight: 64,
        columns: 12
    },
    {
        name: "Items",
        firstgid: 49,
        imageSrc: "./Assets/Level/Tilesets/Items.png",
        tileWidth: 64,
        tileHeight: 64,
        columns: 1
    },
    {
        name: "Inside",
        firstgid: 54,
        imageSrc: "./Assets/Level/Tilesets/Inside.png",
        tileWidth: 64,
        tileHeight: 64,
        columns: 12
    },
    {
        name: "Extra",
        firstgid: 150,
        imageSrc: "./Assets/Level/Tilesets/Extra.png",
        tileWidth: 64,
        tileHeight: 64,
        columns: 11
    },
    /*{
        name: "Objects",
        firstgid: 216,
        imageSrc: "./Assets/Level/Tilesets/Objects.png",
        tileWidth: 64,   // max tile size; objects vary
        tileHeight: 64,
        columns: 4       // dummy; object tiles are individual, but this is enough to render
    },*/
    {
        name: "Platforms",
        firstgid: 244,
        imageSrc: "./Assets/Level/Tilesets/Platforms.png",
        tileWidth: 64,
        tileHeight: 64,
        columns: 4
    },
    {
        name: "Grass",
        firstgid: 252,
        imageSrc: "./Assets/Level/Tilesets/Grass.png",
        tileWidth: 64,
        tileHeight: 64,
        columns: 5
    }
];

export async function loadTilesets() {
    const tilesetsWithImages = [];
    for (const tileset of TILESETS) {
        const image = await loadImage(tileset.imageSrc);
        tilesetsWithImages.push({...tileset, image});
    }
    return tilesetsWithImages;
}

function findTilesetForGid(tilesets, gid) {
    let result = null;
    for (const tileset of tilesets) {
        if (gid >= tileset.firstgid) {
            if (!result || tileset.firstgid > result.firstgid) {
                result = tileset;
            }
        }
    }
    return result;
}

export function drawTileLayer(ctx, camera, layer, tilesets, tileWidth, tileHeight) {
    if (!layer || layer.type !== "tilelayer" || !layer.visible) return;
    const { width, height, data } = layer;

    for (let row = 0; row < height; row++) {
        for (let col = 0; col < width; col++) {
            const index = row * width + col;
            const gid = data[index];

            if (!gid) continue;

            const tileset = findTilesetForGid(tilesets, gid);
            if (!tileset) continue;

            const localId = gid - tileset.firstgid;
            const sx = (localId % tileset.columns) * tileset.tileWidth;
            const sy = Math.floor(localId / tileset.columns) * tileset.tileHeight;

            const dx = col * tileWidth - camera.x;
            const dy = row * tileHeight - camera.y;

            ctx.drawImage(tileset.image, sx, sy, tileset.tileWidth, tileset.tileHeight, dx, dy, tileWidth, tileHeight);
        }
    }
}

export function getLayerByName(map, name) {
    return map.layers.find(layer => layer.name === name);
}

export function buildPlatformsFromLayer(map, layerName, options = {}) {
    const layer = getLayerByName(map, layerName);
    if (!layer || layer.type !== "tilelayer") return [];

    const { width, height, data } = layer;
    const tileWidth = map.tilewidth;
    const tileHeight = map.tileheight;

    const {
        mode = "full",
        thickness = 16,
        offsetFromBottom = 0,
        oneWay = false
    } = options;

    const platforms = [];

    for (let row = 0; row < height; row++) {
        for (let col = 0; col < width; col++) {
            const index = row * width + col;
            const gid = data[index];
            if (!gid) continue;

            const x = col * tileWidth;
            let y = row * tileHeight;
            let h = tileHeight;

            if (mode === "top") {
                h = thickness;
                const tileBottom = (row + 1) * tileHeight;
                y = tileBottom - offsetFromBottom - h;
            }

            platforms.push(
                new Platform({
                    x,
                    y,
                    width: tileWidth,
                    height: h,
                    oneWay
                })
            );
        }
    }

    return platforms;
}