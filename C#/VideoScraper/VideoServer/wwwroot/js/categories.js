document.addEventListener("DOMContentLoaded", () => {
    const tagList = document.getElementById("tag-list");
    if (!tagList) return;

    tagList.querySelectorAll("li a").forEach(a => {
        a.addEventListener("mouseenter", () => {
            a.classList.add("hovered");
        });
        a.addEventListener("mouseleave", () => {
            a.classList.remove("hovered");
        });
    });
    
    const sortedLis = Array.from(tagList.children)
        .sort((li1, li2) => li1.textContent.localeCompare(li2.textContent, undefined, { sensitivity: "base" }));

    tagList.innerHTML = "";
    sortedLis.forEach(li => tagList.appendChild(li));
});
