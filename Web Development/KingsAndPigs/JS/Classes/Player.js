import {Sprite} from "./Sprite.js";

import {canvas, ctx} from '../Index.js';

export class Player extends Sprite {
    constructor({collisionBlocks = [], imageSrc}) {
        super({imageSrc});

        this.position = {
            x: 100,
            y: 100
        }

        this.velocity = {
            x: 0,
            y: 0
        }

        this.width = 100;
        this.height = 100;

        this.gravity = 1.2;
        this.speed = 5;
        this.defaultJumpSpeed = 20;
        this.isOnGround = false;

        this.collisionBlocks = collisionBlocks;
    }

    update(keys) {
        ctx.fillStyle = 'rgba(0, 0, 255, 0.5)';
        ctx.fillRect(this.position.x, this.position.y, this.width, this.height);

        this.moveHorizontal(keys);
        this.checkHorizontalCollisions();

        this.applyGravity();
        this.checkVerticalCollisions();

        this.draw();
    }

    moveHorizontal(keys) {
        if (keys.d.pressed) {
            this.velocity.x = this.speed;
        } else if (keys.a.pressed) {
            this.velocity.x = -this.speed;
        } else {
            this.velocity.x = 0;
        }

        this.position.x += this.velocity.x;
    }

    jump(jumpHeight = this.defaultJumpSpeed) {
        if (this.isOnGround) {
            this.velocity.y = -jumpHeight;
            this.isOnGround = false;
        }
    }

    applyGravity() {
        this.velocity.y += this.gravity;
        this.position.y += this.velocity.y;
    }

    checkHorizontalCollisions() {
        for (const block of this.collisionBlocks) {
            if (!this.isColliding(block)) {
                continue;
            }

            if (this.velocity.x > 0) {
                this.position.x = block.position.x - this.width;
            } else if (this.velocity.x < 0) {
                this.position.x = block.position.x + block.width;
            }

            this.velocity.x = 0;
        }
    }

    checkVerticalCollisions() {
        this.isOnGround = false;

        for (const block of this.collisionBlocks) {
            if (!this.isColliding(block)) {
                continue;
            }

            if (this.velocity.y > 0) {
                this.position.y = block.position.y - this.height;
                this.velocity.y = 0;
                this.isOnGround = true;
            } else if (this.velocity.y < 0) {
                this.position.y = block.position.y + block.height;
                this.velocity.y = 0;
            }
        }

        if (this.position.y + this.height >= canvas.height) {
            this.position.y = canvas.height - this.height;
            this.velocity.y = 0;
            this.isOnGround = true;
        }
    }

    isColliding(block) {
        return (
            this.position.x < block.position.x + block.width &&
            this.position.x + this.width > block.position.x &&
            this.position.y < block.position.y + block.height &&
            this.position.y + this.height > block.position.y
        );
    }
}