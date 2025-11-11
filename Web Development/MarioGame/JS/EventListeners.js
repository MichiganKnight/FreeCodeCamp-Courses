import {player, keys} from './Index.js';

export function setupEventListeners() {
    addEventListener('keydown', (event) => {
        switch (event.code) {
            case "KeyA":
                keys.a.pressed = true;

                break;
            case "KeyD":
                keys.d.pressed = true;

                break;
            case "KeyW":
            case "Space":
                if (event.repeat) {
                    return;
                }

                player.velocity.y -= 20;

                break;
        }
    });

    addEventListener('keyup', (event) => {
        switch (event.code) {
            case "KeyA":
                keys.a.pressed = false;

                break;
            case "KeyD":
                keys.d.pressed = false;

                break;
            case "KeyW":
            case "Space":
                if (event.repeat) {
                    return;
                }

                player.velocity.y = 0;

                break;
        }
    });
}