// EventHandlers.js

import {
    editorCanvas,
    setCameraX, setCameraY,
    setLastMouseX,
    setLastMouseY,
    setLeftDown,
    setMiddleDown,
    setRightDown
} from "./Variables.js";
import { cameraX, cameraY, zoom, isLeftDown, isMiddleDown, isRightDown, lastMouseX, lastMouseY, } from "./Variables.js";
import { paintAtMouse } from "./Painting.js";

// --------------
// Event Handlers
// --------------

export function handleEditorMouseDown(e) {
    if (e.button === 0) {
        setLeftDown(true);
        paintAtMouse(e);
    } else if (e.button === 1) {
        e.preventDefault();
        setMiddleDown(true);
    } else if (e.button === 2) {
        setRightDown(true);
    }
    const rect = editorCanvas.getBoundingClientRect();
    setLastMouseX(e.clientX - rect.left);
    setLastMouseY(e.clientY - rect.top);
}

export function handleEditorMouseMove(e) {
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

        setCameraX(cameraX - dx / zoom);
        setCameraY(cameraY - dy / zoom);
    }
    setLastMouseX(mx);
    setLastMouseY(my);
}

export function handleEditorMouseUp(e) {
    if (e.button === 0) {
        setLeftDown(false);
    } else if (e.button === 1) {
        setMiddleDown(false);
    } else if (e.button === 2) {
        setRightDown(false);
    }
}

editorCanvas.addEventListener("contextmenu", e => e.preventDefault());