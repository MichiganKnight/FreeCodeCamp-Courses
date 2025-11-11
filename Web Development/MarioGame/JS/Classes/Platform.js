import {canvas, ctx} from "../Index.js";

export class Platform {
    constructor({x, y, color = "blue"}) {
        this.position = {
            x,
            y,
        }

        this.color = color;

        this.width = 200;
        this.height = 20;
    }

    draw() {
        ctx.fillStyle = this.color;
        ctx.fillRect(this.position.x, this.position.y, this.width, this.height);
    }
}