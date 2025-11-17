import { canvas, ctx, camera, level } from "../Game.js";
import { Entity } from "./Entity.js";
import { applyGravityAndCollisions } from "./Physics.js";

export class Player extends Entity {
    constructor({position, imageSrc}) {
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

        this.animations = {
            idle: {frames: this.loadFrames("Idle", 5), frameBuffer: 16},
            run: {frames: this.loadFrames("Run", 5), frameBuffer: 10},
            jump: {frames: this.loadFrames("Jump", 1), frameBuffer: 18},
            fall: {frames: this.loadFrames("Fall", 1), frameBuffer: 18},
            attack: {frames: this.loadFrames("Attack", 3), frameBuffer: 10},
            air_attack: {frames: this.loadFrames("Air_Attack", 3), frameBuffer: 10}
        };

        this.image = this.animations.idle.frames[0];

        this.isAttacking = false;
        this.isJumping = false;

        this.collider = {
            top: 22,
            bottom: 18,
            left: 32,
            right: 32
        };
    }

    getColliderOffsets() {
        return {
            top: this.collider.top,
            bottom: this.collider.bottom,
            left: this.collider.left,
            right: this.collider.right
        };
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

        const onGroundOrPlatform = applyGravityAndCollisions(
            this,
            platforms,
            level.height,
            this.getColliderOffsets()
        );

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

        const prevFrame = this.currentFrame;
        this.updateFrame();

        const currentAnim = this.animations[this.state];
        if (
            this.isAttacking &&
            (this.state === "attack" || this.state === "air_attack") &&
            currentAnim &&
            prevFrame === currentAnim.frames.length - 1 &&
            this.currentFrame === 0
        ) {
            this.isAttacking = false;
        }

        const playerWidth = this.width;
        const playerHeight = this.height;

        const targetCameraX = this.position.x - canvas.width / 2 + playerWidth / 2;
        const targetCameraY = this.position.y - canvas.height / 2 + playerHeight / 2;
        const easing = 0.1;

        camera.x += (targetCameraX - camera.x) * easing;
        camera.y += (targetCameraY - camera.y) * easing;

        camera.x = Math.max(0, Math.min(camera.x, level.width - canvas.width));
        camera.y = Math.max(0, Math.min(camera.y, level.height - canvas.height));

        if (this.position.x < 0) this.position.x = 0;
        if (this.position.x + playerWidth > level.width) {
            this.position.x = level.width - playerWidth;
        }
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