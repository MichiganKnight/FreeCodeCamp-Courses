import {
    editorCanvas,
    editorCtx,
    paletteCanvas,
    paletteCtx,
    tilesetSelect,
    showGridCheckbox, setTilesetCols
} from "./Variables.js";

import {
    tileSize,
    levelWidth,
    levelHeight,
    cameraX,
    cameraY,
    zoom,
    tilesetImage,
    selectedTileIndex,
    tiles,
    playerObject
} from "./Variables.js";

import { loadedTilesetCols, loadedTilesetImages } from "./Init.js";

// ---------
// Rendering
// ---------

export function drawPalette() {
    paletteCtx.clearRect(0, 0, paletteCanvas.width, paletteCanvas.height);

    if (!tilesetImage || tilesetImage.width === 0) return;

    const cols = Math.floor(tilesetImage.width / tileSize);
    const rows = Math.floor(tilesetImage.height / tileSize);

    setTilesetCols(cols);

    paletteCtx.drawImage(tilesetImage, 0, 0);
    paletteCtx.strokeStyle = "#555";
    paletteCtx.lineWidth = 1;

    for (let x = 0; x < cols; x++) {
        paletteCtx.beginPath();
        paletteCtx.moveTo(x * tileSize + 0.5, 0);
        paletteCtx.lineTo(x * tileSize + 0.5, rows * tileSize);
        paletteCtx.stroke();
    }

    for (let y = 0; y < rows; y++) {
        paletteCtx.beginPath();
        paletteCtx.moveTo(0, y * tileSize + 0.5);
        paletteCtx.lineTo(cols * tileSize, y * tileSize + 0.5);
        paletteCtx.stroke();
    }

    if (selectedTileIndex >= 1) {
        const index = selectedTileIndex - 1;
        const col = index % cols;
        const row = Math.floor(index / cols);

        const selectedOption = tilesetSelect.options[tilesetSelect.selectedIndex];
        const selectedText = selectedOption ? selectedOption.text : "";
        const isFourByFourTileset = ["Outside", "Inside", "Extra", "Spikes"].some(name => selectedText.includes(name));

        paletteCtx.strokeStyle = "#ff0";
        paletteCtx.lineWidth = 2;

        if (isFourByFourTileset) {
            const blockWidth = 4 * tileSize;
            const blockHeight = 4 * tileSize;

            paletteCtx.strokeRect(
                col * tileSize + 1,
                row * tileSize + 1,
                blockWidth - 2,
                blockHeight - 2
            );
        } else {
            paletteCtx.strokeRect(
                col * tileSize + 1,
                row * tileSize + 1,
                tileSize - 2,
                tileSize - 2
            );
        }
    }
}

export function drawGrid() {
    if (!showGridCheckbox.checked) return;

    let startX = Math.floor(cameraX / tileSize);
    let startY = Math.floor(cameraY / tileSize);
    let endX = Math.ceil((cameraX + editorCanvas.width / zoom) / tileSize);
    let endY = Math.ceil((cameraY + editorCanvas.height / zoom) / tileSize);

    startX = Math.max(0, startX);
    startY = Math.max(0, startY);
    endX = Math.min(levelWidth, endX);
    endY = Math.min(levelHeight, endY);

    const mapLeft = (0 - cameraX) * zoom;
    const mapTop = (0 - cameraY) * zoom;
    const mapRight = (levelWidth * tileSize - cameraX) * zoom;
    const mapBottom = (levelHeight * tileSize - cameraY) * zoom;

    editorCtx.save();
    editorCtx.strokeStyle = "rgba(255,255,255,0.15)";
    editorCtx.lineWidth = 1 / zoom;

    for (let x = startX; x <= endX; x++) {
        const sx = (x * tileSize - cameraX) * zoom + 0.5;

        if (sx < mapLeft - 1 || sx > mapRight + 1) continue;

        const lineTop = Math.max(0, Math.max(mapTop, 0));
        const lineBottom = Math.min(editorCanvas.height, Math.min(mapBottom, editorCanvas.height));

        if (lineBottom <= lineTop) continue;

        editorCtx.beginPath();
        editorCtx.moveTo(sx, lineTop);
        editorCtx.lineTo(sx, lineBottom);
        editorCtx.stroke();
    }

    for (let y = startY; y <= endY; y++) {
        const sy = (y * tileSize - cameraY) * zoom + 0.5;

        if (sy < mapTop - 1 || sy > mapBottom + 1) continue;

        const lineLeft = Math.max(0, Math.max(mapLeft, 0));
        const lineRight = Math.min(editorCanvas.width, Math.min(mapRight, editorCanvas.width));

        if (lineRight <= lineLeft) continue;

        editorCtx.beginPath();
        editorCtx.moveTo(lineLeft, sy);
        editorCtx.lineTo(lineRight, sy);
        editorCtx.stroke();
    }

    editorCtx.restore();
}

export function drawTileLayer() {
    if (!tiles || tiles.length === 0) return;

    const cols = levelWidth;

    for (let ty = 0; ty < levelHeight; ty++) {
        for (let tx = 0; tx < levelWidth; tx++) {
            const index = ty * cols + tx;

            const tile = tiles[index];
            if (!tile) continue;

            const { gid, tileset } = tile;

            const tsImage = loadedTilesetImages[tileset];
            const tsCols = loadedTilesetCols[tileset];

            if (!tsImage) continue;

            const srcIndex = gid - 1;
            const sxTile = srcIndex % tsCols;
            const syTile = Math.floor(srcIndex / tsCols);

            const sx = sxTile * tileSize;
            const sy = syTile * tileSize;

            const dx = (tx * tileSize - cameraX) * zoom;
            const dy = (ty * tileSize - cameraY) * zoom;
            const dw = tileSize * zoom;
            const dh = tileSize * zoom;

            editorCtx.drawImage(
                tsImage,
                sx, sy, tileSize, tileSize,
                dx, dy, dw, dh
            );
        }
    }
}

export function drawPlayerObject() {
    if (!playerObject) return;

    const px = (playerObject.x - cameraX) * zoom;
    const py = (playerObject.y - playerObject.height - cameraY) * zoom;
    const pw = playerObject.width * zoom;
    const ph = playerObject.height * zoom;

    editorCtx.save();
    editorCtx.fillStyle = "rgba(0, 200, 255, 0.35)";
    editorCtx.fillRect(px, py, pw, ph);
    editorCtx.strokeStyle = "rgba(0, 200, 255, 1)";
    editorCtx.lineWidth = 2;
    editorCtx.strokeRect(px, py, pw, ph);

    editorCtx.fillStyle = "white";
    editorCtx.font = `${10 * zoom}px sans-serif`;
    editorCtx.fillText("Player", px + 2 * zoom, py + 10 * zoom);

    editorCtx.restore();
}