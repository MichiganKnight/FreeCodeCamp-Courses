import { formatTime } from "./video-player.js";

document.addEventListener("DOMContentLoaded", () => {
    const imagePosters = document.querySelectorAll(".image-poster");
    const watchButtons = document.querySelectorAll(".watch-button");

    const videoContainer = document.getElementById("video-player-container");
    const videoPlayer = document.getElementById("video-player");
    const videoSource = document.getElementById("video-source");

    setupLazyThumbnailLoading(imagePosters, watchButtons);

    watchButtons.forEach(button => {
        button.addEventListener("click", async () => {
            const videoUrl = button.dataset.videoUrl;
            if (!videoUrl) return;

            if (videoContainer.style.display === "none")
                videoContainer.style.display = "block";

            // Generate a random poster for the player
            generateRandomPoster(videoUrl, videoPlayer);

            videoSource.src = videoUrl;
            videoPlayer.load();
            videoContainer.scrollIntoView({ behavior: "smooth" });
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
                const videoUrl = watchButtons[index].dataset.videoUrl;
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
        imgElement.src = canvas.toDataURL("image/png");
        
        console.log(imgElement.width, imgElement.height);
    } catch (err) {
        console.error("Error Generating Random Poster:", err);
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