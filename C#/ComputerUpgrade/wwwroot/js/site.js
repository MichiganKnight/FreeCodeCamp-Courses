document.addEventListener("DOMContentLoaded", (event) => {
    const image = document.getElementById("rotatingImage");
    
    if (image) {
        const randomDirection = Math.random() < 0.5 ? 1 : -1;
        
        const minSpeed = 1;
        const maxSpeed = 8;
        const randomDuration = (Math.random() * (maxSpeed - minSpeed) + minSpeed).toFixed(2) + 's';
        
        console.log(`Image is Rotating: ${randomDirection} at ${randomDuration}`);
        
        image.style.setProperty("--rotation-direction", randomDirection);
        image.style.setProperty("--rotation-duration", randomDuration);
    }
});