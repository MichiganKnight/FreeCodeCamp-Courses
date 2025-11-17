import { ctx, camera } from "../Game.js"

export class Platform {
    constructor({x, y, width, height, color = "maroon", oneWay = false}) {
        this.position = {x, y};
        this.width = width;
        this.height = height;
        this.color = color;
        this.oneWay = oneWay;
    }

    draw() {
        ctx.fillStyle = this.color;
        ctx.fillRect(this.position.x - camera.x, this.position.y - camera.y, this.width, this.height);
    }
}