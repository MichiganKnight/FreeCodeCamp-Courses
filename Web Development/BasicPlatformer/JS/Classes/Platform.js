import { ctx, camera } from "../Index.js"

export class Platform {
    constructor({x, y, width, height, color = "maroon"}) {
        this.position = {x, y};
        this.width = width;
        this.height = height;
        this.color = color;
    }

    draw() {
        ctx.fillStyle = this.color;
        ctx.fillRect(this.position.x - camera.x, this.position.y - camera.y, this.width, this.height);
    }
}