import {togglePlay, showTooltip, seek_player, formatTime} from "./video-player.js";
import {videoPlayer} from "./video-player.js";

document.addEventListener("DOMContentLoaded", () => {
    const title = document.title.replace(" - VideoServer", "");
    const map = {
        "TNA Videos": "../Data/TNA.json",
        "SpankBang Videos": "../Data/SpankBang.json",
        "Local Videos": "../Data/Local.json"
    };

    if (map[title]) {
        loadVideos(map[title]);
    }
});

// ========== FETCH & DISPLAY ==========
async function loadVideos(jsonFile) {
    try {
        const response = await fetch(jsonFile);

        if (!response.ok) {
            throw new Error(`HTTP Error! Status: ${response.status}`);
        }

        const videos = await response.json();

        if (!Array.isArray(videos)) {
            console.error(`Expected JSON array but got:`, videos);
            return;
        }

        const dropdownMenu = document.getElementById("dropdown-menu");
        const positionList = document.getElementById("positionList");
        dropdownMenu.innerHTML = "";

        videos.forEach((video, index) => {
            const li = document.createElement("li");
            const a = document.createElement("a");

            a.className = "dropdown-item";
            a.href = "#";
            a.textContent = video.Name;
            a.dataset.index = index;
            a.addEventListener("click", e => {
                e.preventDefault();
                loadVideo(videos[index]);
                renderPositions(videos[index].Positions, positionList);
            });

            li.appendChild(a);
            dropdownMenu.appendChild(li);
        });

        if (videos.length > 0) {
            loadVideo(videos[0]);
            renderPositions(videos[0].Positions, positionList);
        }
    } catch (error) {
        console.error(`Error Fetching Data: ${error}`);
    }
}

// ========== LOAD SINGLE VIDEO ==========
async function loadVideo(video) {
    const videoSource = document.getElementById("video-source");
    const videoName = document.getElementById("video-name");
    const dropdownButton = document.getElementById("dropdown-button");

    videoPlayer.pause();
    /*if (video.Poster && video.Poster.trim() !== "") {
        videoPlayer.poster = video.Poster.startsWith("http")
            ? `https://corsproxy.io/?${encodeURIComponent(video.Poster)}`
            : video.Poster;
    } else {
        await generateRandomPoster(video.Source, videoPlayer);
    }*/
    
    await generateRandomPoster(video.Source, videoPlayer);

    videoSource.src = video.Source;
    videoPlayer.load();

    videoName.textContent = video.Name;
    dropdownButton.textContent = video.Name;
    document.title = video.Name;
}

// ========== POSITIONS ==========
function renderPositions(positions = [], list) {
    list.innerHTML = "";
    positions.forEach(pos => list.appendChild(createPositionItem(pos)));
}

function createPositionItem(position) {
    const li = document.createElement("li");
    const img = document.createElement("img");
    const p = document.createElement("p");

    // Placeholder image
    img.src = "data:image/svg+xml,%3Csvg xmlns='http://www.w3.org/2000/svg' width='160' height='90'%3E%3Crect fill='%23333' width='160' height='90'/%3E%3Ctext fill='%23fff' x='50%25' y='50%25' dominant-baseline='middle' text-anchor='middle'%3ELoading...%3C/text%3E%3C/svg%3E";
    img.alt = position.Name;
    Object.assign(img.style, { width: "160px", height: "90px", objectFit: "cover", cursor: "pointer" });

    const name = document.createElement("span");
    name.textContent = position.Name;
    name.style.display = "block";
    name.style.fontWeight = "600";

    const time = document.createElement("span");
    time.textContent = formatTime(position.Timestamp);
    time.style.fontSize = "13px";

    Object.assign(p.style, { textAlign: "center", cursor: "pointer", lineHeight: "1.2" });
    p.append(name, time);
    li.append(img, p);

    generateThumbnail(position.Timestamp, img);
    li.addEventListener("click", () => seek_player(position.Timestamp));
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

// ========== RANDOM POSTER FALLBACK ==========
async function generateRandomPoster(videoUrl, player) {
    try {
        const tempVideo = document.createElement("video");
        Object.assign(tempVideo, { src: videoUrl, crossOrigin: "anonymous", muted: true, playsInline: true });

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
        player.poster = canvas.toDataURL("image/png");
    } catch (err) {
        console.error("Error Generating Random Poster:", err);
    }
}

// ========== KEYBOARD SHORTCUTS ==========
document.addEventListener("keydown", (e) => {
    const activeTag = document.activeElement.tagName.toLowerCase();
    if (["input", "textarea", "select"].includes(activeTag)) return;

    switch (e.key) {
        case " ":
            e.preventDefault();
            togglePlay();
            break;
        case "ArrowRight":
            videoPlayer.currentTime = Math.min(videoPlayer.currentTime + 5, videoPlayer.duration);
            showTooltip("+5s");
            break;
        case "ArrowLeft":
            videoPlayer.currentTime = Math.max(videoPlayer.currentTime - 5, 0);
            showTooltip("-5s");
            break;
        case "ArrowUp":
            e.preventDefault();
            videoPlayer.volume = Math.min(videoPlayer.volume + 0.05, 1);
            volumeControl.value = videoPlayer.volume;
            showTooltip(`Volume: ${Math.round(videoPlayer.volume * 100)}%`);
            break;
        case "ArrowDown":
            e.preventDefault();
            videoPlayer.volume = Math.max(videoPlayer.volume - 0.05, 0);
            volumeControl.value = videoPlayer.volume;
            showTooltip(`Volume: ${Math.round(videoPlayer.volume * 100)}%`);
            break;
        case "KeyM":
            videoPlayer.muted = !videoPlayer.muted;
            break;
        case "KeyF":
            if (!document.fullscreenElement) videoContainer.requestFullscreen();
            else document.exitFullscreen();
            break;
        case "KeyP":
            if (document.pictureInPictureElement) document.exitPictureInPicture();
            else if (document.pictureInPictureEnabled) videoPlayer.requestPictureInPicture();
            break;
    }
});