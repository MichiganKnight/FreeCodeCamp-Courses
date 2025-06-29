/**
 * Loads and inserts the content of the Navbar HTML file into the element
 * with the ID "navbar-include" by fetching it asynchronously.
 *
 * @return {Promise<void>} A promise that resolves when the Navbar content
 * has been successfully fetched and inserted into the DOM.
 */
async function loadIncludes() {
    document.getElementById("navbar-include").innerHTML = await fetch('/shared/Navbar.html').then(res => res.text());
}

/**
 * Loads code from a given endpoint, formats it by removing certain lines, and displays it in an HTML element.
 * Optionally highlights the code if Prism.js is available.
 *
 * @param {string} endpoint The URL from which to fetch the code.
 * @param {string} elementId The ID of the HTML element where the formatted code will be displayed.
 * @return {Promise<void>} A promise that resolves when the code is fetched, processed, and displayed.
 */
async function loadCode(endpoint, elementId) {
    const code = await fetch(endpoint).then(res => res.text());
    const lines = code.split('\n');
    const packageIndex = lines.findIndex(line => line.trim().startsWith('package'));

    if (packageIndex !== -1) {
        lines.splice(packageIndex, 1);

        if (lines[packageIndex]?.trim() === '') {
            lines.splice(packageIndex, 1);
        }
    }

    const filtered = lines.join('\n').trim();
    const el = document.getElementById(elementId);
    el.textContent = filtered;

    if (window.Prism) {
        Prism.highlightElement(el);
    }
}

/**
 * Attaches click event listeners to elements with the 'copy-btn' class.
 * These buttons, when clicked, copy the text content of a target element
 * (identified by the button's data-target attribute) to the clipboard.
 * Temporarily updates the button's text to indicate success.
 */
function setupCopyButtons() {
    document.querySelectorAll('.copy-btn').forEach(btn => {
        btn.addEventListener('click', () => {
            const targetId = btn.dataset.target;
            const code = document.getElementById(targetId).textContent;

            navigator.clipboard.writeText(code).then(() => {
                const original = btn.innerText;
                btn.innerText = "✅ Copied!";
                setTimeout(() => btn.innerText = original, 1500);
            });
        });
    });
}

/**
 * Renders and initializes interactive code blocks within the document.
 * It searches for elements marked with the `code-block` class, constructs the necessary HTML structure for displaying
 * syntax-highlighted code, and attaches a "Copy Code" button. The method also loads the code content asynchronously
 * using the `loadCode` function.
 */
function renderCodeBlocks() {
    document.querySelectorAll('.code-block').forEach(async block => {
        const id = block.dataset.id;
        const src = block.dataset.src;

        block.innerHTML = `
            <div class="position-relative" style="width: 100%; max-width: 800px; min-width: 650px">
                <button class="copy-btn btn btn-sm btn-secondary position-absolute top-0 end-0 m-2 z-1" style="transform: translateX(0.5rem)" title="Copy Code" data-target="${id}">📋 Copy</button>
                <pre class="line-numbers"><code id="${id}" class="language-java"></code></pre>
            </div>`;

        await loadCode(src, id);
    });
}

document.addEventListener('DOMContentLoaded', async () => {
    await loadIncludes();

    renderCodeBlocks();
    setupCopyButtons();
});