import {
    applySizeBtn,
    cameraX,
    cameraY,
    clearPlayerBtn,
    downloadJsonBtn,
    editorCanvas,
    editorCtx,
    levelHeight,
    levelHeightInput,
    levelWidth,
    levelWidthInput,
    paletteCanvas,
    paletteInfo,
    setTileImage,
    setTiles, setTilesetCols,
    tilesetCols,
    tilesetImage,
    tilesetSelect,
    tileSize,
    setSelectedTileIndex,
    setPlayerObject,
    setLevelSize,
    setZoom, zoom, setCameraX, setCameraY
} from "./Variables.js";

import { handleEditorMouseDown, handleEditorMouseMove, handleEditorMouseUp } from "./EventHandlers.js";

import { drawGrid, drawPalette, drawPlayerObject, drawTileLayer } from "./Renderer.js";

import { downloadJson, updateStatus } from "./UI.js";

// caches for loaded tilesets (keyed by tilesetSelect.value)
export const loadedTilesetImages = {};
export const loadedTilesetCols = {};

// --------------
// Initialization
// --------------

function initTilesArray() {
    const totalTiles = levelWidth * levelHeight;
    // use null for empty because we store objects for filled tiles
    const newTilesArray = new Array(totalTiles).fill(null);
    setTiles(newTilesArray);
}

function initTileset() {
    const url = tilesetSelect.value;
    if (!url) {
        updateStatus("No tileset selected");
        return;
    }

    // if we already loaded it, reuse it
    if (loadedTilesetImages[url]) {
        setTileImage(loadedTilesetImages[url]);
        setTilesetCols(loadedTilesetCols[url]);
        drawPalette();
        return;
    }

    const tempImage = new Image();
    tempImage.src = url;

    tempImage.onload = () => {
        const cols = Math.floor(tempImage.width / tileSize);
        // cache
        loadedTilesetImages[url] = tempImage;
        loadedTilesetCols[url] = cols;

        setTilesetCols(cols);
        setTileImage(tempImage);

        drawPalette();
    };

    tempImage.onerror = () => {
        updateStatus("Failed to Load Tileset Image");
    };
}

export function init() {
    initTilesArray();
    initTileset();
    setupEvents();

    requestAnimationFrame(loop);
}

// --------------
// Event Handling
// --------------

function setupEvents() {
    applySizeBtn.addEventListener("click", () => {
        const newWidth = parseInt(levelWidthInput.value, 10) || levelWidth;
        const newHeight = parseInt(levelHeightInput.value, 10) || levelHeight;

        setLevelSize(newWidth, newHeight);

        // tileSize remains same unless you add UI for it
        // re-init tiles array
        initTilesArray();

        if (tilesetImage.complete && tilesetImage.naturalWidth > 0) {
            setTilesetCols(Math.floor(tilesetImage.width / tileSize));
            drawPalette();
        }

        updateStatus(`Level Size: ${newWidth} x ${newHeight}`);
    });

    tilesetSelect.addEventListener("change", () => {
        initTileset();
    });

    // Palette mouse
    paletteCanvas.addEventListener("mousedown", handlePaletteMouseDown);

    // Editor canvas mouse
    editorCanvas.addEventListener("mousedown", handleEditorMouseDown);
    editorCanvas.addEventListener("mousemove", handleEditorMouseMove);
    window.addEventListener("mouseup", handleEditorMouseUp);

    editorCanvas.addEventListener("auxclick", e => {
        if (e.button === 1) {
            e.preventDefault();
        }
    });

    editorCanvas.addEventListener("wheel", e => {
        e.preventDefault();

        const zoomFactor = 1.1;
        const oldZoom = zoom;

        let newZoom = zoom;
        if (e.deltaY < 0) {
            newZoom = zoom * zoomFactor
        } else {
            newZoom = zoom / zoomFactor
        }

        newZoom = Math.max(0.5, Math.min(6, newZoom));
        setZoom(newZoom);

        const rect = editorCanvas.getBoundingClientRect();
        const mx = e.clientX - rect.left;
        const my = e.clientY - rect.top;
        const worldX = cameraX + mx / oldZoom;
        const worldY = cameraY + my / oldZoom;

        setCameraX(worldX - mx / newZoom);
        setCameraY(worldY - my / newZoom);

    }, { passive: false });

    clearPlayerBtn.addEventListener("click", () => {
        setPlayerObject(null);
        updateStatus("Player Spawn Cleared");
    });

    downloadJsonBtn.addEventListener("click", downloadJson);

    function handlePaletteMouseDown(e) {
        const rect = paletteCanvas.getBoundingClientRect();
        const x = e.clientX - rect.left;
        const y = e.clientY - rect.top;

        const col = Math.floor(x / tileSize);
        const row = Math.floor(y / tileSize);

        if (col < 0 || row < 0) return;
        if (!tilesetImage || tilesetCols <= 0) return;

        const selectedOption = tilesetSelect.options[tilesetSelect.selectedIndex];
        const selectedText = selectedOption ? selectedOption.text : "";
        const isFourByFourTileset = ["Outside", "Inside", "Extra", "Spikes"].some(name => selectedText.includes(name));

        let baseCol = col;
        let baseRow = row;

        if (isFourByFourTileset) {
            baseCol = col - (col % 4);
            baseRow = row - (row % 4);
        }

        const newIndex = baseRow * tilesetCols + baseCol + 1;
        setSelectedTileIndex(newIndex);

        paletteInfo.textContent = `Selected Tile Index: ${newIndex}`;
        drawPalette();
    }
}

function loop() {
    editorCtx.clearRect(0, 0, editorCanvas.width, editorCanvas.height);

    editorCtx.save();

    editorCtx.fillStyle = "#1e1e1e";
    editorCtx.fillRect(0, 0, editorCanvas.width, editorCanvas.height);

    drawTileLayer();
    drawPlayerObject();
    drawGrid();

    editorCtx.restore();

    requestAnimationFrame(loop);
}