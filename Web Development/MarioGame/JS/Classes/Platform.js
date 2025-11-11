import {canvas, ctx} from "../Index.js";

export class Platform {
    constructor() {
        this.position = {
            x: 200,
            y: 555
        }

        this.width = 200;
        this.height = 20;
    }

    draw() {
        ctx.fillStyle = "blue";
        ctx.fillRect(this.position.x, this.position.y, this.width, this.height);
    }
}