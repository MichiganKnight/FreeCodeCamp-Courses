import {canvas, ctx} from "../Index.js";

export class Player {
    constructor() {
        this.position = {
            x: 100,
            y: 100
        }

        this.velocity = {
            x: 0,
            y: 1
        }

        this.width = 30;
        this.height = 30;

        this.gravity = 1.2;
    }

    draw() {
        ctx.fillStyle = "maroon";
        ctx.fillRect(this.position.x, this.position.y, this.width, this.height);
    }

    update() {
        this.draw();

        this.position.x += this.velocity.x;
        this.position.y += this.velocity.y;

        if (this.position.y + this.height + this.velocity.y <= canvas.height) {
            this.velocity.y += this.gravity;
        } else {
            this.velocity.y = 0;
        }
    }
}