// --------------
// Site Variables
// --------------

export const editorCanvas = document.getElementById("editor-canvas");
export const editorCtx = editorCanvas.getContext("2d");

export const paletteCanvas = document.getElementById("palette-canvas");
export const paletteCtx = paletteCanvas.getContext("2d");

export const levelWidthInput = document.getElementById("level-width");
export const levelHeightInput = document.getElementById("level-height");
export const applySizeBtn = document.getElementById("apply-size-btn");

export const tilesetSelect = document.getElementById("tileset-select");
export const paletteInfo = document.getElementById("palette-info");

export const showGridCheckbox = document.getElementById("show-grid");
export const eraseModeCheckbox = document.getElementById("erase-mode");
export const snapBlocksCheckbox = document.getElementById("snap-blocks");

export const clearPlayerBtn = document.getElementById("clear-player-btn");
export const downloadJsonBtn = document.getElementById("download-json-btn");
export const statusEl = document.getElementById("status");

// ------------
// Editor State (exported as mutable lets + setters)
// ------------

export let tileSize = 16;

const parseOrDefault = (val, def) => {
    const n = parseInt(val, 10);
    return Number.isFinite(n) ? n : def;
};

export let levelWidth = parseOrDefault(levelWidthInput?.value, 80);
export let levelHeight = parseOrDefault(levelHeightInput?.value, 40);

export function setLevelSize(w, h) {
    levelWidth = w;
    levelHeight = h;
}

export let cameraX = 0;
export let cameraY = 0;
export function setCameraX(x) {
    cameraX = x;
}
export function setCameraY(y) {
    cameraY = y;
}

export let zoom = 1;
export function setZoom(z) {
    zoom = z;
}

export let isLeftDown = false;
export let isMiddleDown = false;
export let isRightDown = false;
export function setLeftDown(newState) {
    isLeftDown = newState;
}
export function setMiddleDown(newState) {
    isMiddleDown = newState;
}
export function setRightDown(newState) {
    isRightDown = newState;
}

export let lastMouseX = 0;
export let lastMouseY = 0;
export function setLastMouseX(newX) {
    lastMouseX = newX;
}
export function setLastMouseY(newY) {
    lastMouseY = newY;
}

export let tilesetImage = new Image();
export function setTileImage(newImg) {
    tilesetImage = newImg;
}

export let tilesetCols = 0;
export function setTilesetCols(newCols) {
    tilesetCols = newCols;
}

export let selectedTileIndex = 1;
export function setSelectedTileIndex(i) {
    selectedTileIndex = i;
}

export let tiles = []; // will store either null or { gid: number, tileset: string }
export function setTiles(newTiles) {
    tiles = newTiles;
}

export let playerObject = null; // { name, x, y, width, height }
export function setPlayerObject(obj) {
    playerObject = obj;
}

export function updateStatus(text) {
    if (statusEl) statusEl.textContent = text;
}