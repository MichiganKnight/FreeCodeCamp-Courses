export function setupEventListeners(keys, player) {
    window.addEventListener('keydown', (event) => {
        switch (event.code) {
            case 'KeyA':
                keys.a.pressed = true;

                break;
            case 'KeyD':
                keys.d.pressed = true;

                break;
            case 'Space':
                player.jump();

                break;
        }
    });

    window.addEventListener('keyup', (event) => {
        switch (event.code) {
            case 'KeyA':
                keys.a.pressed = false;

                break;
            case 'KeyD':
                keys.d.pressed = false;

                break;
        }
    });
}