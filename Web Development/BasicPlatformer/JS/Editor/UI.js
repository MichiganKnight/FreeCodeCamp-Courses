import { statusEl } from "./Variables.js";

import { buildLevelJson } from "./JSON.js";

export function downloadJson() {
    const json = JSON.stringify(buildLevelJson(), null, 2);

    console.log("JSON Data:", json);

    /*const blob = new Blob([json], {type: "application/json"});
    const url = URL.createObjectURL(blob);

    const a = document.createElement("a");
    a.href = url;

    const fileName = `Level-${Date.now()}.json`;
    a.download = fileName;
    a.click();

    URL.revokeObjectURL(url);

    updateStatus(`JSON Downloaded as "${fileName}"`);*/
}

// -------
// Utility
// -------

export function updateStatus(text) {
    statusEl.textContent = text;
}