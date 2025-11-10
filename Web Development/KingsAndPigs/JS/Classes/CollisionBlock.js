export class CollisionBlock {
    constructor({position, ctx}) {
        this.position = position;
        this.ctx = ctx;
        this.width = 64;
        this.height = 64;
    }

    draw() {
        this.ctx.fillStyle = 'rgba(255, 0, 0, 0.5)';
        this.ctx.fillRect(this.position.x, this.position.y, this.width, this.height);
    }
}