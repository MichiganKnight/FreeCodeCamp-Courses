const videoPlayer = document.getElementById("video-player");
const tooltip = document.getElementById("shortcut-tooltip");
const videoContainer = document.getElementById("video-container");
const playPauseBtn = document.getElementById("playPauseBtn");
const bigPlayBtn = document.getElementById("bigPlayBtn");
const progressContainer = document.getElementById("progressContainer");
const progressBar = document.getElementById("progressBar");
const progressDot = document.getElementById("progressDot");
const volumeControl = document.getElementById("volumeControl");
const volumeBtn = document.getElementById("volumeBtn");
const fsBtn = document.getElementById("fsBtn");
const pipBtn = document.getElementById("pipBtn");
const controlsBar = document.getElementById("controlsBar");
const timeDisplay = document.getElementById("timeDisplay");

let hideTimeout;

document.addEventListener("DOMContentLoaded", () => {
    getVideos();
});

function formatTime(seconds) {
    if (isNaN(seconds)) {
        return "00:00:00";
    }

    const h = Math.floor(seconds / 3600);
    const m = Math.floor((seconds % 3600) / 60);
    const s = Math.floor(seconds % 60);

    return [h, m, s].map(v => String(v).padStart(2, '0')).join(':');
}

function togglePlay() {
    if (videoPlayer.paused) {
        videoPlayer.play();

        playPauseBtn.innerHTML = '<i class="bi bi-pause-fill"></i>';
        bigPlayBtn.classList.add("hidden");
    } else {
        videoPlayer.pause();

        playPauseBtn.innerHTML = '<i class="bi bi-play-fill"></i>';
        bigPlayBtn.classList.remove("hidden");
    }
}

playPauseBtn.addEventListener("click", togglePlay);
bigPlayBtn.addEventListener("click", togglePlay);
videoPlayer.addEventListener("click", togglePlay);

function showTooltip(text) {
    tooltip.textContent = text;
    tooltip.classList.add("show");

    clearTimeout(tooltip.timeout);
    tooltip.timeout = setTimeout(() => {
        tooltip.classList.remove("show");
    }, 800);
}

document.addEventListener("keydown", (event) => {
    const activeElement = document.activeElement.tagName.toLowerCase();

    if (["input", "textarea", "select"].includes(activeElement)) {
        return;
    }

    switch (event.key) {
        case " ":
            event.preventDefault();
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
            event.preventDefault();
            videoPlayer.volume = Math.min(videoPlayer.volume + 0.05, 1);
            document.getElementById("volumeControl").value = videoPlayer.volume;
            showTooltip(`Volume: ${Math.round(videoPlayer.volume * 100)}%`);
            checkVolume(videoPlayer.volume);
            break;
        case "ArrowDown":
            event.preventDefault();
            videoPlayer.volume = Math.max(videoPlayer.volume - 0.05, 0);
            document.getElementById("volumeControl").value = videoPlayer.volume;
            showTooltip(`Volume: ${Math.round(videoPlayer.volume * 100)}%`);
            checkVolume(videoPlayer.volume);
            break;
        case "KeyM":
            videoPlayer.muted = !videoPlayer.muted;
            checkVolume(videoPlayer.volume);
            break;
        case "KeyF":
            if (!document.fullscreenElement) {
                videoContainer.requestFullscreen();
            } else {
                document.exitFullscreen();
            }
            break;
        case "KeyP":
            if (document.pictureInPictureElement) {
                document.exitPictureInPicture();
            } else if (document.pictureInPictureEnabled) {
                videoPlayer.requestPictureInPicture();
            }
            break;
    }
});

videoPlayer.addEventListener("timeupdate", () => {
    const percent = (videoPlayer.currentTime / videoPlayer.duration) * 100;

    progressBar.style.width = `${percent}%`;
    progressDot.style.left = `${percent}%`;
    timeDisplay.textContent = `${formatTime(videoPlayer.currentTime)} / ${formatTime(videoPlayer.duration)}`;
});

videoPlayer.addEventListener("loadedmetadata", () => {
    timeDisplay.textContent = `00:00:00 / ${formatTime(videoPlayer.duration)}`;
});

progressContainer.addEventListener("click", (e) => {
    const width = progressContainer.clientWidth;
    const clickX = e.offsetX;

    videoPlayer.currentTime = (clickX / width) * videoPlayer.duration;
});

volumeBtn.addEventListener("click", () => {
    videoPlayer.muted = !videoPlayer.muted;

    checkVolume(videoPlayer.volume);
});

function checkVolume(volume) {
    if (videoPlayer.muted || volume <= 0.01) {
        volumeBtn.innerHTML = '<i class="bi bi-volume-mute"></i>';
    } else if (volume > 0.5) {
        volumeBtn.innerHTML = '<i class="bi bi-volume-up"></i>';
    } else {
        volumeBtn.innerHTML = '<i class="bi bi-volume-down"></i>';
    }
}

volumeControl.addEventListener("input", (e) => {
    videoPlayer.volume = volumeControl.value;

    checkVolume(videoPlayer.volume);
});

document.querySelectorAll(".playback-speed").forEach(item => {
    item.addEventListener("click", e => {
        videoPlayer.playbackRate = parseFloat(e.target.getAttribute("data-speed"));
    });
});

pipBtn.addEventListener("click", async () => {
    if (document.pictureInPictureElement) {
        await document.exitPictureInPicture()
    } else {
        await document.requestPictureInPicture();
    }
});

fsBtn.addEventListener("click", () => {
    if (!document.fullscreenElement) {
        videoContainer.requestFullscreen();
    } else {
        document.exitFullscreen();
    }
});

videoPlayer.addEventListener("ended", () => {
    playPauseBtn.innerHTML = '<i class="bi bi-play-fill"></i>';
    bigPlayBtn.classList.remove("hidden");

    progressBar.style.width = '0%';
    progressDot.style.width = '0%';
});

function showControls() {
    controlsBar.classList.remove("hide");

    clearTimeout(hideTimeout);
    hideTimeout = setTimeout(() => {
        if (!videoPlayer.paused) {
            controlsBar.classList.add("hide");
        }
    }, 2500);
}

videoContainer.addEventListener("mousemove", showControls);
videoContainer.addEventListener("mouseleave", () => {
    if (!videoPlayer.paused) {
        controlsBar.classList.add("hide");
    }
});

videoPlayer.addEventListener("play", showControls);
videoPlayer.addEventListener("pause", () => {
    controlsBar.classList.remove("hide");
});

showControls();

function getVideos() {
    const dropdownMenu = document.getElementById("dropdown-menu");
    const videoSource = document.getElementById("video-source");
    const videoName = document.getElementById("video-name");
    const dropdownButton = document.getElementById("dropdown-button");

    fetch('TNA_Videos.json')
        .then(response => response.json())
        .then(videos => {
            videos.forEach((video, index) => {
                const li = document.createElement("li");
                const a = document.createElement("a");

                a.className = "dropdown-item";
                a.href = '#';
                a.textContent = video.name;
                a.dataset.index = index;

                li.appendChild(a);

                dropdownMenu.appendChild(li);

                a.addEventListener('click', e => {
                    e.preventDefault();

                    const selectedVideo = videos[a.dataset.index];

                    videoPlayer.pause();
                    videoPlayer.poster = selectedVideo.poster;
                    videoSource.src = selectedVideo.source;

                    videoPlayer.load();

                    videoName.textContent = selectedVideo.name;
                    document.title = selectedVideo.name;
                    dropdownButton.textContent = selectedVideo.name;
                });
            });

            if (videos.length > 0) {
                const firstVideo = videos[0];

                videoPlayer.pause();
                videoPlayer.poster = firstVideo.poster;
                videoSource.src = firstVideo.source;

                videoPlayer.load();

                videoName.textContent = firstVideo.name;
                document.title = firstVideo.name;
                dropdownButton.textContent = firstVideo.name;
            }
        })
        .catch(error => console.error('Error loading videos:', error));
}