document.addEventListener("DOMContentLoaded", () => {
    const watchButtons = document.querySelectorAll(".watch-button");
    const videoContainer = document.getElementById("video-player-container");
    const videoPlayer = document.getElementById("video-player");
    const videoSource = document.getElementById("video-source");

    watchButtons.forEach(button => {
        button.addEventListener("click", () => {
            const videoUrl = button.dataset.videoUrl;
            if (!videoUrl) return;
            
            if (videoContainer.style.display === "none") {
                videoContainer.style.display = "block";
            }
            
            generateRandomPoster(videoUrl, videoPlayer);
            
            videoSource.src = videoUrl;
            videoPlayer.load();

            videoContainer.scrollIntoView({ behavior: "smooth" });
        });
    });
});

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