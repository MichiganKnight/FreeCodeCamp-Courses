import { canvas, ctx, camera, level } from "../Game.js";
import { Entity } from "./Entity.js";
import { applyGravityAndCollisions } from "./Physics.js";

export class Player extends Entity {
    constructor({ position, imageSrc }) {
        super({
            position,
            speed: 5,
            gravity: 0.8,
            imageSrc,
            animations: {},
            initialState: "idle",
            frameBuffer: 6,
            facing: "Right"
        });

        // Load all animations
        this.animations = {
            idle: { frames: this.loadFrames("Idle", 5), frameBuffer: 16 },
            run: { frames: this.loadFrames("Run", 5), frameBuffer: 10 },
            jump: { frames: this.loadFrames("Jump", 1), frameBuffer: 18 },
            fall: { frames: this.loadFrames("Fall", 1), frameBuffer: 18 },
            attack: { frames: this.loadFrames("Attack", 3), frameBuffer: 10 },
            air_attack: { frames: this.loadFrames("Air_Attack", 3), frameBuffer: 10 }
        };

        this.image = this.animations.idle.frames[0];

        this.isAttacking = false;
        this.isJumping = false;

        // Collider offsets
        this.collider = { top: 22, bottom: 18, left: 32, right: 32 };
    }

    getColliderOffsets() {
        return this.collider;
    }

    attack() {
        if (this.isAttacking) return;
        this.isAttacking = true;
        this.setState(this.isJumping ? "air_attack" : "attack");
    }

    jump() {
        if (this.isJumping) return;
        this.velocity.y = -18;
        this.isJumping = true;
        this.setState("jump");
    }

    moveHorizontal(keys) {
        this.velocity.x = 0;
        if (keys.d?.pressed) {
            this.velocity.x = this.speed;
            this.facing = "Right";
        } else if (keys.a?.pressed) {
            this.velocity.x = -this.speed;
            this.facing = "Left";
        }
    }

    update(keys, platforms) {
        this.moveHorizontal(keys);

        // Apply gravity and collisions
        const onGroundOrPlatform = applyGravityAndCollisions(
            this,
            platforms,
            level.height,
            this.getColliderOffsets()
        );

        // Update animation state
        if (!this.isAttacking) {
            if (!onGroundOrPlatform) {
                this.isJumping = true;
                this.setState(this.velocity.y < 0 ? "jump" : "fall");
            } else {
                this.isJumping = false;
                const movingHorizontally = keys.a?.pressed || keys.d?.pressed;
                this.setState(movingHorizontally ? "run" : "idle");
            }
        }

        // Handle attack animation completion
        const prevFrame = this.currentFrame;
        this.updateFrame();
        const currentAnim = this.animations[this.state];
        if (
            this.isAttacking &&
            currentAnim &&
            (this.state === "attack" || this.state === "air_attack") &&
            prevFrame === currentAnim.frames.length - 1 &&
            this.currentFrame === 0
        ) {
            this.isAttacking = false;
        }

        // Camera follows player with easing
        const playerWidth = this.width;
        const playerHeight = this.height;

        const targetCameraX = this.position.x - canvas.width / 2 + playerWidth / 2;
        const targetCameraY = this.position.y - canvas.height / 2 + playerHeight / 2;
        const easing = 0.1;

        camera.x += (targetCameraX - camera.x) * easing;
        camera.y += (targetCameraY - camera.y) * easing;

        // Clamp camera to level bounds
        camera.x = Math.max(0, Math.min(camera.x, level.width - canvas.width));
        camera.y = Math.max(0, Math.min(camera.y, level.height - canvas.height));

        // Clamp player position within level bounds
        if (this.position.x < 0) this.position.x = 0;
        if (this.position.x + playerWidth > level.width) this.position.x = level.width - playerWidth;
        if (this.position.y < 0) this.position.y = 0;
        if (this.position.y + playerHeight > level.height) this.position.y = level.height - playerHeight;
    }

    draw() {
        if (!this.image) return;

        ctx.save();

        if (this.facing === "Left") {
            ctx.scale(-1, 1);
            ctx.drawImage(
                this.image,
                -this.position.x - this.image.width + camera.x,
                this.position.y - camera.y
            );
        } else {
            ctx.drawImage(
                this.image,
                this.position.x - camera.x,
                this.position.y - camera.y
            );
        }

        ctx.restore();
    }
}
