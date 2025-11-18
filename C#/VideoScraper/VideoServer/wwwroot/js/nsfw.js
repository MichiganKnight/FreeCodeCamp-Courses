import { seek_player, formatTime, initVideoPlayer, videoPlayer } from "./video-player.js";

document.addEventListener("DOMContentLoaded", () => {
    initVideoPlayer();

    const imagePosters = document.querySelectorAll(".image-poster");
    const watchButtons = document.querySelectorAll(".watch-button");
    const videoSource = document.getElementById("video-source");

    const tagWrapper = document.getElementById("tag-wrapper");
    const tagList = document.getElementById("tag-list");

    const positionWrapper = document.getElementById("position-wrapper");
    const positionList = document.getElementById("position-list");

    setupLazyThumbnailLoading(imagePosters, watchButtons);

    watchButtons.forEach(button => {
        button.addEventListener("click", async () => {
            const videoName = button.getAttribute("data-name")?.trim() || "";
            if (!videoName) return;
            
            const videoUrl = button.getAttribute("data-url")?.trim() || "";
            if (!videoUrl) return;
            
            const videoNameElement = document.getElementById("video-name");
            videoNameElement.textContent = videoName;

            const videoContainer = document.getElementById("video-player-container");
            const tags = button.getAttribute("data-tags")?.split(",").filter(tag => tag.trim() !== "") || [];
            const positions = JSON.parse(button.dataset.positions || "[]");

            tagList.innerHTML = "";
            if (tags.length > 0) {
                tags.forEach(tag => {
                    const tagElement = document.createElement("li");
                    const tagAnchor = document.createElement("a");
                    tagElement.classList.add("tag");
                    tagAnchor.textContent = tag.trim();
                    tagAnchor.href = `/NSFWVideos/Category?category=${encodeURIComponent(tag.trim())}`;
                    tagElement.appendChild(tagAnchor);
                    tagList.appendChild(tagElement);
                });
                tagWrapper.style.display = "block";
            } else {
                tagWrapper.style.display = "none";
            }

            videoContainer.style.display = "block";

            await generateRandomPoster(videoUrl, videoPlayer);

            videoSource.src = videoUrl;
            videoPlayer.load();

            videoContainer.scrollIntoView({ behavior: "smooth" });

            if (positions.length > 0) {
                positionList.innerHTML = '';
                positions.forEach(pos => {
                    const item = createPositionItem(pos);
                    if (item) positionList.appendChild(item);
                });
                positionWrapper.style.display = "block";
            } else {
                positionWrapper.style.display = "none";
            }
        });
    });
});


// ========== LAZY LOADING WITH THROTTLING ========== //
function setupLazyThumbnailLoading(imagePosters, watchButtons, maxConcurrent = 3) {    
    const queue = [];
    let active = 0;

    const runNext = async () => {
        if (queue.length === 0 || active >= maxConcurrent) return;

        const { img, videoUrl } = queue.shift();
        
        active++;
        try {
            await generateRandomImage(videoUrl, img);
        } catch (err) {
            console.error("Thumb error:", err);
        } finally {
            active--;
            runNext();
        }
    };

    const observer = new IntersectionObserver((entries) => {
        entries.forEach(entry => {
            if (entry.isIntersecting) {
                const img = entry.target;
                const index = Array.from(imagePosters).indexOf(img);
                const videoUrl = watchButtons[index].dataset.url;
                if (!videoUrl) return;

                // Add to queue only once
                observer.unobserve(img);
                queue.push({ img, videoUrl });
                runNext();
            }
        });
    }, { rootMargin: "200px 0px" }); // preload slightly before visible

    imagePosters.forEach(img => observer.observe(img));
}

// ========== RANDOM THUMBNAIL ========== //
async function generateRandomImage(videoUrl, imgElement) {    
    const tempVideo = document.createElement("video");
    tempVideo.src = videoUrl;
    tempVideo.crossOrigin = "anonymous"; // important for CORS
    tempVideo.muted = true;
    tempVideo.playsInline = true;
    tempVideo.preload = "auto";

    try {
        // Wait for metadata to load
        await new Promise((resolve, reject) => {
            tempVideo.onloadedmetadata = () => resolve();
            tempVideo.onerror = () => reject("Failed to load video metadata");
        });

        // Pick random time (less than duration - 0.1s)
        const randomTime = Math.min(Math.random() * tempVideo.duration, tempVideo.duration - 0.1);
        tempVideo.currentTime = randomTime;

        // Wait for seeked event
        await new Promise((resolve, reject) => {
            tempVideo.onseeked = () => resolve();
            tempVideo.onerror = () => reject("Seek failed");
        });

        // Draw to canvas
        const canvas = document.createElement("canvas");
        canvas.width = tempVideo.videoWidth;
        canvas.height = tempVideo.videoHeight;
        canvas.getContext("2d").drawImage(tempVideo, 0, 0, canvas.width, canvas.height);

        imgElement.src = canvas.toDataURL("image/png");
    } catch (err) {
        console.warn("Failed to generate random thumbnail:", err);
        // fallback placeholder
        imgElement.src = "data:image/svg+xml,%3Csvg xmlns='http://www.w3.org/2000/svg' width='160' height='90'%3E%3Crect fill='%23333' width='160' height='90'/%3E%3Ctext fill='%23fff' x='50%25' y='50%25' dominant-baseline='middle' text-anchor='middle'%3EUnavailable%3C/text%3E%3C/svg%3E";
    }
}

// ========== RANDOM POSTER FOR PLAYER ========== //
async function generateRandomPoster(videoUrl, videoElement) {
    try {
        const tempVideo = document.createElement("video");
        Object.assign(tempVideo, {
            src: videoUrl,
            crossOrigin: "anonymous",
            muted: true,
            playsInline: true
        });

        await new Promise((res, rej) => {
            tempVideo.onloadedmetadata = res;
            tempVideo.onerror = rej;
        });

        tempVideo.currentTime = Math.random() * tempVideo.duration;
        await new Promise(res => tempVideo.onseeked = res);

        const canvas = document.createElement("canvas");
        canvas.width = tempVideo.videoWidth;
        canvas.height = tempVideo.videoHeight;
        canvas.getContext("2d").drawImage(tempVideo, 0, 0, canvas.width, canvas.height);
        videoElement.poster = canvas.toDataURL("image/png");
    } catch (err) {
        console.error("Error Generating Random Poster:", err);
    }
}

// ========== POSITIONS ==========
function createPositionItem(position) {
    const li = document.createElement("li");
    const img = document.createElement("img");
    const p = document.createElement("p");
    
    const timestamp = Number(position.Timestamp);
    if (!isFinite(timestamp) || timestamp < 0) {
        console.error("Invalid Timestamp:", position.Timestamp);
        return null;
    }

    // Placeholder image
    img.src = "data:image/svg+xml,%3Csvg xmlns='http://www.w3.org/2000/svg' width='160' height='90'%3E%3Crect fill='%23333' width='160' height='90'/%3E%3Ctext fill='%23fff' x='50%25' y='50%25' dominant-baseline='middle' text-anchor='middle'%3ELoading...%3C/text%3E%3C/svg%3E";
    img.alt = position.Name;
    Object.assign(img.style, { width: "160px", height: "90px", objectFit: "cover", cursor: "pointer" });

    const name = document.createElement("span");
    name.textContent = position.Name;
    name.style.display = "block";
    name.style.fontWeight = "600";

    const time = document.createElement("span");
    time.textContent = formatTime(timestamp);
    time.style.fontSize = "13px";

    Object.assign(p.style, { textAlign: "center", cursor: "pointer", lineHeight: "1.2" });
    p.append(name, time);
    li.append(img, p);

    generateThumbnail(timestamp, img);
    li.addEventListener("click", () => seek_player(timestamp));
    
    return li;
}

// ========== THUMBNAIL GENERATION ==========
function generateThumbnail(timestamp, imgElement) {
    const src = document.getElementById("video-source")?.src;

    if (!src) {
        return;
    }

    const tempVideo = document.createElement("video");
    Object.assign(tempVideo, {crossOrigin: "anonymous", preload: "auto", muted: true, src});

    tempVideo.addEventListener("loadedmetadata", () => {        
        tempVideo.currentTime = Math.min(timestamp, tempVideo.duration - 0.5);
    });

    tempVideo.addEventListener("seeked", () => {
        try {
            const canvas = document.createElement("canvas");
            canvas.width = 160;
            canvas.height = 90;
            canvas.getContext("2d").drawImage(tempVideo, 0, 0, 160, 90);
            imgElement.src = canvas.toDataURL("image/png");
        } catch {
            imgElement.src = "data:image/svg+xml,%3Csvg xmlns='http://www.w3.org/2000/svg' width='160' height='90'%3E%3Crect fill='%23f00' width='160' height='90'/%3E%3Ctext fill='%23fff' x='50%25' y='50%25'%3ECORS%20Error%3C/text%3E%3C/svg%3E";
        }
    }, {once: true});
}