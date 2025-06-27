async function loadIncludes() {
    document.getElementById("navbar-include").innerHTML = await fetch('/shared/Navbar.html').then(res => res.text());
}

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