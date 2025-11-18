// ========== ELEMENT REFERENCES ==========
const videoContainer = document.getElementById("video-container");
export const videoPlayer = document.getElementById("video-player");
const playPauseBtn = document.getElementById("playPauseBtn");
const bigPlayBtn = document.getElementById("bigPlayBtn");
const progressContainer = document.getElementById("progressContainer");
const progressBar = document.getElementById("progressBar");
const progressDot = document.getElementById("progressDot");
const volumeBtn = document.getElementById("volumeBtn");
const volumeControl = document.getElementById("volumeControl");
const controlsBar = document.getElementById("controlsBar");
const timeDisplay = document.getElementById("timeDisplay");
const pipBtn = document.getElementById("pipBtn");
const fsBtn = document.getElementById("fsBtn");
const tooltip = document.getElementById("shortcut-tooltip");

let hideTimeout;

// ========== CONTROL VISIBILITY ==========
function showControls() {
    controlsBar.classList.remove("hidden");
    clearTimeout(hideTimeout);
    hideTimeout = setTimeout(() => {
        if (!videoPlayer.paused) {
            controlsBar.classList.add("hidden");
        }
    }, 3000);
}

function hideControls() {
    if (!videoPlayer.paused) {
        controlsBar.classList.add("hidden");
    }
}

// ========== PLAYBACK TOGGLE ==========
function togglePlay() {
    if (videoPlayer.paused) {
        videoPlayer.play().catch(err => console.error("Error Playing Video:", err));
        playPauseBtn.innerHTML = '<i class="bi bi-pause-fill"></i>';
        bigPlayBtn.classList.add("hidden");
    } else {
        videoPlayer.pause();
        playPauseBtn.innerHTML = '<i class="bi bi-play-fill"></i>';
        bigPlayBtn.classList.remove("hidden");
    }
}

// ========== PROGRESS ==========
function setupProgress() {
    videoPlayer.addEventListener("timeupdate", () => {
        const percent = (videoPlayer.currentTime / videoPlayer.duration) * 100 || 0;
        progressBar.style.width = `${percent}%`;
        progressDot.style.left = `${percent}%`;
        timeDisplay.textContent = `${formatTime(videoPlayer.currentTime)} / ${formatTime(videoPlayer.duration)}`;
    });

    progressContainer.addEventListener("click", (e) => {
        const clickX = e.offsetX;
        videoPlayer.currentTime = (clickX / progressContainer.clientWidth) * videoPlayer.duration;
    });
}

// ========== VOLUME ==========
function checkVolume(volume) {
    if (videoPlayer.muted || volume <= 0.01) {
        volumeBtn.innerHTML = '<i class="bi bi-volume-mute"></i>';
    } else if (volume > 0.5) {
        volumeBtn.innerHTML = '<i class="bi bi-volume-up"></i>';
    } else {
        volumeBtn.innerHTML = '<i class="bi bi-volume-down"></i>';
    }
}

function setupVolume() {
    volumeBtn.addEventListener("click", () => {
        videoPlayer.muted = !videoPlayer.muted;
        checkVolume(videoPlayer.volume);
    });

    volumeControl.addEventListener("input", () => {
        videoPlayer.volume = volumeControl.value;
        checkVolume(videoPlayer.volume);
    });
}

// ========== EXTRA FEATURES ==========
function setupExtraFeatures() {
    document.querySelectorAll(".playback-speed").forEach(item => {
        item.addEventListener("click", e => {
            videoPlayer.playbackRate = parseFloat(e.target.dataset.speed);
        });
    });

    pipBtn.addEventListener("click", async () => {
        if (document.pictureInPictureElement) {
            await document.exitPictureInPicture();
        } else if (document.pictureInPictureEnabled) {
            await videoPlayer.requestPictureInPicture();
        }
    });

    fsBtn.addEventListener("click", () => {
        if (!document.fullscreenElement) {
            videoContainer.requestFullscreen();
        } else {
            document.exitFullscreen();
        }
    });
}

// ========== KEYBOARD SHORTCUTS ==========
function setupKeyboardShortcuts() {
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
}

// ========== SETUP CONTROLS & PLAYBACK ==========
function setupControls() {
    showControls();
    videoContainer.addEventListener("mousemove", showControls);
    videoContainer.addEventListener("mouseleave", hideControls);

    playPauseBtn.addEventListener("click", (e) => {
        e.stopPropagation();
        togglePlay();
    });

    bigPlayBtn.addEventListener("click", (e) => {
        e.stopPropagation();
        togglePlay();
    });

    videoPlayer.addEventListener("click", (e) => {
        if (e.target === videoPlayer) togglePlay();
    });
}

function setupPlayback() {
    videoPlayer.addEventListener("ended", () => {
        playPauseBtn.innerHTML = '<i class="bi bi-play-fill"></i>';
        bigPlayBtn.classList.remove("hidden");
        progressBar.style.width = '0%';
        progressDot.style.left = '0%';
    });
}

// ========== UTILITIES ==========
export function seek_player(timeInSeconds) {
    videoPlayer.pause();
    videoPlayer.currentTime = timeInSeconds;
}

export function formatTime(seconds) {
    if (isNaN(seconds)) return "00:00:00";
    const h = Math.floor(seconds / 3600);
    const m = Math.floor((seconds % 3600) / 60);
    const s = Math.floor(seconds % 60);
    return [h, m, s].map(v => String(v).padStart(2, '0')).join(':');
}

export function showTooltip(text) {
    tooltip.textContent = text;
    tooltip.classList.add("show");
    clearTimeout(tooltip.timeout);
    tooltip.timeout = setTimeout(() => tooltip.classList.remove("show"), 800);
}

// ========== INIT ==========
export function initVideoPlayer() {
    setupControls();
    setupPlayback();
    setupProgress();
    setupVolume();
    setupExtraFeatures();
    setupKeyboardShortcuts();
}

// Automatically init when DOM is ready
document.addEventListener("DOMContentLoaded", initVideoPlayer);