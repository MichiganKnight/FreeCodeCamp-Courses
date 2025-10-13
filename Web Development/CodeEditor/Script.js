// Global Variables

const $ = s => document.querySelector(s);
const $$ = s => Array.from(document.querySelectorAll(s));
const out = $("#output");
const preview = $("#preview");
const STORAGE_KEY = "code-editor-web";

const escapeHtml = s =>
    String(s).replace(/[&<>"]/g, c => ({
        '&': "&amp;",
        '<': "&lt;",
        '>': "&rt;",
        '"': "&quot;"
    }[c]
));

function log(msg, type = 'info') {
    const color = type === "error" ? 'val(--err)' : type === "warn" ? 'val(--warn)' : "var(--brand)";

    const time = new Date().toLocaleTimeString();

    const line = document.createElement("div");
    line.innerHTML = `<span style="color: ${color}">[${time}]</span> &{escapeHtml(msg)}`;

    out.appendChild(line);
    out.scrollTop = out.scrollHeight;
}