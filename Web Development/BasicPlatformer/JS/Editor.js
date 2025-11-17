const editorCanvas = document.getElementById("editor-canvas");
const editorCtx = editorCanvas.getContext("2d");

const paletteCanvas = document.getElementById("palette-canvas");
const paletteCtx = paletteCanvas.getContext("2d");

const levelWidthInput = document.getElementById("level-width");
const levelHeightInput = document.getElementById("level-height");
const applySizeBtn = document.getElementById("apply-size-btn");

const tilesetSelect = document.getElementById("tileset-select");
const paletteInfo = document.getElementById("palette-info");

const showGridCheckbox = document.getElementById("show-grid");
const eraseModeCheckbox = document.getElementById("erase-mode");
const snapBlocksCheckbox = document.getElementById("snap-blocks");

const clearPlayerBtn = document.getElementById("clear-player-btn");
const downloadJsonBtn = document.getElementById("download-json-btn");
const statusEl = document.getElementById("status");

// ------------
// Editor State
// ------------

let tileSize = 16;
let levelWidth = parseInt(levelWidthInput.value, 10 || 80);
let levelHeight = parseInt(levelHeightInput.value, 10 || 40);

let cameraX = 0;
let cameraY = 0;
let zoom = 1;

let isLeftDown = false;
let isMiddleDown = false;
let isRightDown = false;
let lastMouseX = 0;
let lastMouseY = 0;

let tilesetImage = new Image();
let tilesetCols = 0;
let selectedTileIndex = 1;

let tiles = [];

let playerObject = null;

// --------------
// Initialization
// --------------

function initTilesArray() {
    const totalTiles = levelWidth * levelHeight;
    tiles = new Array(totalTiles).fill(0);
}

function initTileset() {
    tilesetImage = new Image();
    tilesetImage.src = tilesetSelect.value;

    tilesetImage.onload = () => {
        tilesetCols = Math.floor(tilesetImage.width / tileSize);
        drawPalette();
    };

    tilesetImage.onerror = () => {
        updateStatus("Failed to load tileset image.");
    };
}

function init() {
    initTilesArray();
    initTileset();
    setupEvents();

    requestAnimationFrame(loop);
}

window.addEventListener("load", init);

// --------------
// Event Handling
// --------------

function setupEvents() {
    applySizeBtn.addEventListener("click", () => {
        const newWidth = parseInt(levelWidthInput.value, 10) || levelWidth;
        const newHeight = parseInt(levelHeightInput.value, 10) || levelHeight;

        levelWidth = newWidth;
        levelHeight = newHeight;

        tileSize = 16;

        initTilesArray();

        if (tilesetImage.complete && tilesetImage.naturalWidth > 0) {
            tilesetCols = Math.floor(tilesetImage.width / tileSize);
            drawPalette();
        }

        updateStatus(`Level Size: ${levelWidth} x ${levelHeight}`);
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

        if (e.deltaY < 0) {
            zoom *= zoomFactor;
        } else {
            zoom /= zoomFactor;
        }

        zoom = Math.max(0.5, Math.min(6, zoom));

        const rect = editorCanvas.getBoundingClientRect();
        const mx = e.clientX - rect.left;
        const my = e.clientY - rect.top;
        const worldX = cameraX + mx / oldZoom;
        const worldY = cameraY + my / oldZoom;

        cameraX = worldX - mx / zoom;
        cameraY = worldY - my / zoom;
    }, {passive: false});

    clearPlayerBtn.addEventListener("click", () => {
        playerObject = null;
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

        selectedTileIndex = baseRow * tilesetCols + baseCol + 1;

        paletteInfo.textContent = `Selected Tile Index: ${selectedTileIndex}`;
        drawPalette();
    }
}

function handleEditorMouseDown(e) {
    if (e.button === 0) {
        isLeftDown = true;
        paintAtMouse(e);
    } else if (e.button === 1) {
        e.preventDefault();
        isMiddleDown = true;
    } else if (e.button === 2) {
        isRightDown = true;
    }

    const rect = editorCanvas.getBoundingClientRect();
    lastMouseX = e.clientX - rect.left;
    lastMouseY = e.clientY - rect.top;
}

function handleEditorMouseMove(e) {
    const rect = editorCanvas.getBoundingClientRect();
    const mx = e.clientX - rect.left;
    const my = e.clientY - rect.top;

    if (isLeftDown) {
        paintAtMouse(e, false);
    }

    if (isRightDown) {
        paintAtMouse(e, true);
    }

    if (isMiddleDown) {
        e.preventDefault();

        const dx = mx - lastMouseX;
        const dy = my - lastMouseY;
        cameraX -= dx / zoom;
        cameraY -= dy / zoom;
    }

    lastMouseX = mx;
    lastMouseY = my;
}

function handleEditorMouseUp(e) {
    if (e.button === 0) {
        isLeftDown = false;
    } else if (e.button === 1) {
        isMiddleDown = false;
    } else if (e.button === 2) {
        isRightDown = false;
    }
}

editorCanvas.addEventListener("contextmenu", e => e.preventDefault());

function paintAtMouse(e, eraseOverride = false) {
    const rect = editorCanvas.getBoundingClientRect();
    const mx = e.clientX - rect.left;
    const my = e.clientY - rect.top;

    const worldX = cameraX + mx / zoom;
    const worldY = cameraY + my / zoom;

    const tileX = Math.floor(worldX / tileSize);
    const tileY = Math.floor(worldY / tileSize);

    if (e.shiftKey) {
        if (tileX < 0 || tileX >= levelWidth || tileY < 0 || tileY >= levelHeight) return;

        const px = tileX * tileSize;
        const py = tileY * tileSize;

        const playerWidth = tileSize;
        const playerHeight = tileSize * 2;

        playerObject = {
            name: "Player",
            type: "",
            x: px,
            y: py + playerHeight,
            width: playerWidth,
            height: playerHeight
        };

        updateStatus(`Player Spawn at (${tileX}, ${tileY})`);
        return;
    }

    if (!tiles || tiles.length === 0) return;

    let brushSize = 1;
    let brushWidth = brushSize;
    let brushHeight = brushSize;

    const selectedOption = tilesetSelect.options[tilesetSelect.selectedIndex];
    const selectedText = selectedOption ? selectedOption.text : "";
    const isFourByFourTileset =
        ["Outside", "Inside", "Extra", "Spikes"].some(name => selectedText.includes(name));

    if (isFourByFourTileset) {
        brushWidth = 4;
        brushHeight = 4;
    }

    const erase = eraseOverride || eraseModeCheckbox.checked;

    let originX = tileX;
    let originY = tileY;

    if (isFourByFourTileset && snapBlocksCheckbox.checked) {
        originX = tileX - (tileX % 4);
        originY = tileY - (tileY % 4);
    }

    if (!erase && isFourByFourTileset && snapBlocksCheckbox.checked) {
        for (let by = 0; by < brushHeight; by++) {
            for (let bx = 0; bx < brushWidth; bx++) {
                const tx = originX + bx;
                const ty = originY + by;

                if (tx < 0 || ty < 0 || tx >= levelWidth || ty >= levelHeight) continue;

                const idx = ty * levelWidth + tx;
                if (tiles[idx] !== 0) {
                    updateStatus("Cannot place: 4x4 block would overlap existing tiles.");
                    return;
                }
            }
        }
    }

    for (let by = 0; by < brushHeight; by++) {
        for (let bx = 0; bx < brushWidth; bx++) {
            const tx = originX + bx;
            const ty = originY + by;

            if (tx < 0 || ty < 0 || tx >= levelWidth || ty >= levelHeight) continue;

            const index = ty * levelWidth + tx;

            if (erase) {
                tiles[index] = 0;
            } else if (isFourByFourTileset) {
                const baseIndex = selectedTileIndex - 1;
                const baseCol = baseIndex % tilesetCols;
                const baseRow = Math.floor(baseIndex / tilesetCols);

                const colInTileset = baseCol + bx;
                const rowInTileset = baseRow + by;

                const blockIndex = rowInTileset * tilesetCols + colInTileset;
                tiles[index] = blockIndex + 1;
            } else {
                tiles[index] = selectedTileIndex;
            }
        }
    }
}

// ---------
// Rendering
// ---------

function drawPalette() {
    paletteCtx.clearRect(0, 0, paletteCanvas.width, paletteCanvas.height);

    if (!tilesetImage || tilesetImage.width === 0) return;

    const cols = Math.floor(tilesetImage.width / tileSize);
    const rows = Math.floor(tilesetImage.height / tileSize);
    tilesetCols = cols;

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

function drawGrid() {
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

function drawTileLayer(layer) {
    if (!tiles || tiles.length === 0) return;

    const cols = levelWidth;
    const tilesetColsLocal = tilesetCols;

    for (let ty = 0; ty < levelHeight; ty++) {
        for (let tx = 0; tx < levelWidth; tx++) {
            const index = ty * cols + tx;
            const gid = tiles[index];

            if (!gid) continue;

            const srcIndex = gid - 1;
            const sxTile = srcIndex % tilesetColsLocal;
            const syTile = Math.floor(srcIndex / tilesetColsLocal);

            const sx = sxTile * tileSize;
            const sy = syTile * tileSize;

            const dx = (tx * tileSize - cameraX) * zoom;
            const dy = (ty * tileSize - cameraY) * zoom;

            const dw = tileSize * zoom;
            const dh = tileSize * zoom;

            editorCtx.drawImage(
                tilesetImage,
                sx, sy, tileSize, tileSize,
                dx, dy, dw, dh
            );
        }
    }
}

function drawPlayerObject() {
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

function buildLevelJson() {
    const map = {
        width: levelWidth,
        height: levelHeight,
        tilewidth: tileSize,
        tileheight: tileSize,
        orientation: "orthogonal",
        renderorder: "right-down",
        version: "1.9",
        type: "map",
        tiledversion: "1.9.0",
        infinite: false,
        nextlayerid: 3,
        nextobjectid: 2,
        layers: [],
        tilesets: []
    };

    map.layers.push({
        id: 1,
        name: "Terrain",
        type: "tilelayer",
        visible: true,
        opacity: 1,
        x: 0,
        y: 0,
        width: levelWidth,
        height: levelHeight,
        data: tiles.slice() // copy
    });

    const objects = [];
    if (playerObject) {
        objects.push({
            id: 1,
            name: playerObject.name,
            type: playerObject.type,
            x: playerObject.x,
            y: playerObject.y,
            width: playerObject.width,
            height: playerObject.height
        });
    }

    map.layers.push({
        id: 2,
        name: "Objects",
        type: "objectgroup",
        visible: true,
        opacity: 1,
        x: 0,
        y: 0,
        draworder: "topdown",
        objects
    });

    map.tilesets.push({
        firstgid: 1,
        source: null,
        name: "main",
        tilewidth: tileSize,
        tileheight: tileSize,
        tilecount: (tilesetImage.width / tileSize) * (tilesetImage.height / tileSize) || 0,
        columns: tilesetCols,
        image: tilesetSelect.value.replace("./Assets/Level/Tilesets/", ""),
        imagewidth: tilesetImage.width,
        imageheight: tilesetImage.height
    });

    return map;
}

function downloadJson() {
    const json = JSON.stringify(buildLevelJson(), null, 2);

    const blob = new Blob([json], {type: "application/json"});
    const url = URL.createObjectURL(blob);

    const a = document.createElement("a");
    a.href = url;

    const fileName = `Level-${Date.now()}.json`;
    a.download = fileName;
    a.click();

    URL.revokeObjectURL(url);

    updateStatus(`JSON Downloaded as "${fileName}"`);
}

// -------
// Utility
// -------

function updateStatus(text) {
    statusEl.textContent = text;
}