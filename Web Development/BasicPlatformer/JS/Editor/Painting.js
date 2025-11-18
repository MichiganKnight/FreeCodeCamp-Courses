import { editorCanvas, tilesetSelect, eraseModeCheckbox, snapBlocksCheckbox } from "./Variables.js";
import { tileSize, levelWidth, levelHeight, cameraX, cameraY, zoom, tilesetCols, selectedTileIndex, tiles } from "./Variables.js";

import { updateStatus } from "./UI.js";
import { setPlayerObject, setTiles } from "./Variables.js"; // setters we need
import { loadedTilesetCols, loadedTilesetImages } from "./Init.js"; // optional if you need them elsewhere

export function paintAtMouse(e, eraseOverride = false) {
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

        setPlayerObject({
            name: "Player",
            type: "",
            x: px,
            y: py + playerHeight,
            width: playerWidth,
            height: playerHeight
        });

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
                if (tiles[idx] !== null) {
                    updateStatus("Cannot place: 4x4 block would overlap existing tiles.");
                    return;
                }
            }
        }
    }

    // We will mutate the tiles array in place (allowed) and then call setTiles so other modules see the same reference if they rely on the setter pattern
    for (let by = 0; by < brushHeight; by++) {
        for (let bx = 0; bx < brushWidth; bx++) {
            const tx = originX + bx;
            const ty = originY + by;

            if (tx < 0 || ty < 0 || tx >= levelWidth || ty >= levelHeight) continue;

            const index = ty * levelWidth + tx;

            if (erase) {
                tiles[index] = null;
            } else if (isFourByFourTileset) {
                const baseIndex = selectedTileIndex - 1;
                const baseCol = baseIndex % tilesetCols;
                const baseRow = Math.floor(baseIndex / tilesetCols);

                const colInTileset = baseCol + bx;
                const rowInTileset = baseRow + by;

                const blockIndex = rowInTileset * tilesetCols + colInTileset;

                tiles[index] = {
                    gid: blockIndex + 1,
                    tileset: tilesetSelect.value
                };
            } else {
                tiles[index] = {
                    gid: selectedTileIndex,
                    tileset: tilesetSelect.value
                };
            }
        }
    }

    // push the mutated tiles array back through setter in case other modules expect it (keeps parity with setTiles usage)
    setTiles(tiles);
}