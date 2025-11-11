import { Sprite } from "./Sprite.js";
import { canvas, ctx } from '../Index.js';

export class Player extends Sprite {
    constructor({ position = { x: 100, y: 100 }, collisionBlocks = [], imageSrc, frameRate, frameBuffer }) {
        super({ imageSrc, frameRate, frameBuffer });

        this.position = position;
        this.velocity = { x: 0, y: 0 };
        this.width = 100;
        this.height = 100;

        this.gravity = 1.2;
        this.speed = 5;
        this.defaultJumpSpeed = 20;
        this.isOnGround = false;

        this.collisionBlocks = collisionBlocks;

        // Adjusted hitbox for better alignment with 100x100 sprite
        this.hitboxOffset = { x: 25, y: 50, width: 50, height: 50 };
        this.hitbox = {
            position: {
                x: this.position.x + this.hitboxOffset.x,
                y: this.position.y + this.hitboxOffset.y
            },
            width: this.hitboxOffset.width,
            height: this.hitboxOffset.height
        };
    }

    update(keys) {
        this.moveHorizontal(keys);
        this.applyGravity();

        // Update hitbox position
        this.hitbox.position.x = this.position.x + this.hitboxOffset.x;
        this.hitbox.position.y = this.position.y + this.hitboxOffset.y;

        // Draw hitbox for debugging
        ctx.fillStyle = 'rgba(255, 0, 0, 0.5)';
        ctx.fillRect(this.hitbox.position.x, this.hitbox.position.y, this.hitbox.width, this.hitbox.height);
    }

    moveHorizontal(keys) {
        if (keys.d.pressed) this.velocity.x = this.speed;
        else if (keys.a.pressed) this.velocity.x = -this.speed;
        else this.velocity.x = 0;

        let moveAmount = this.velocity.x;

        while (moveAmount !== 0) {
            const step = moveAmount > 0 ? 1 : -1;
            this.position.x += step;
            this.hitbox.position.x = this.position.x + this.hitboxOffset.x;

            for (const block of this.collisionBlocks) {
                if (this.isHitboxColliding(block)) {
                    // Move back to edge
                    this.position.x -= step;
                    this.velocity.x = 0;
                    moveAmount = 0;
                    break;
                }
            }

            // Prevent leaving the canvas horizontally
            if (this.position.x < 0) {
                this.position.x = 0;
                this.velocity.x = 0;
                moveAmount = 0;
            }
            if (this.position.x + this.width > canvas.width) {
                this.position.x = canvas.width - this.width;
                this.velocity.x = 0;
                moveAmount = 0;
            }

            moveAmount -= step;
        }
    }

    jump(jumpHeight = this.defaultJumpSpeed) {
        if (this.isOnGround) {
            this.velocity.y = -jumpHeight;
            this.isOnGround = false;
        }
    }

    applyGravity() {
        this.velocity.y += this.gravity;

        let moveAmount = this.velocity.y;

        while (moveAmount !== 0) {
            const step = moveAmount > 0 ? 1 : -1;
            this.position.y += step;
            this.hitbox.position.y = this.position.y + this.hitboxOffset.y;

            let collided = false;

            for (const block of this.collisionBlocks) {
                if (this.isHitboxColliding(block)) {
                    this.position.y -= step;
                    this.velocity.y = 0;
                    collided = true;
                    if (step > 0) this.isOnGround = true; // landed
                    break;
                }
            }

            // Floor collision
            if (this.position.y + this.hitboxOffset.y + this.hitbox.height >= canvas.height) {
                this.position.y = canvas.height - this.hitbox.height - this.hitboxOffset.y;
                this.velocity.y = 0;
                this.isOnGround = true;
                collided = true;
            }

            if (collided) break;
            moveAmount -= step;
        }
    }

    isHitboxColliding(block) {
        const hb = this.hitbox;
        return (
            hb.position.x < block.position.x + block.width &&
            hb.position.x + hb.width > block.position.x &&
            hb.position.y < block.position.y + block.height &&
            hb.position.y + hb.height > block.position.y
        );
    }
}
