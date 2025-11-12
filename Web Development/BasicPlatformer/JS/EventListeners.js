export function setupEventListeners(keys, player) {
    window.addEventListener("keydown", (event) => {
        switch (event.code) {
            case "KeyD":
                keys.d.pressed = true;
                break;
            case "KeyA":
                keys.a.pressed = true;
                break;
            case "Space":
                if (!player.isJumping) {
                    player.jump();
                }
                break;
            case "KeyE":
                if (!player.isAttacking) {
                    player.attack();
                }
                break;
        }
    });

    window.addEventListener("keyup", (event) => {
        switch (event.code) {
            case "KeyD":
                keys.d.pressed = false;
                break;
            case "KeyA":
                keys.a.pressed = false;
                break;
        }
    })
}