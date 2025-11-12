import { canvas, ctx } from "../Index.js";

export class Player {
    constructor({ position, imageSrc }) {
        this.position = position;
        this.velocity = { x: 0, y: 0 };
        this.speed = 5;
        this.gravity = 0.8;
        this.isAttacking = false;
        this.isJumping = false;
        this.prevY = this.position.y;
        this.prevX = this.position.x;

        this.state = "idle";
        this.frameBuffer = 6;
        this.elapsedFrames = 0;
        this.currentFrame = 0;

        this.facing = "Right";
        this.basePath = imageSrc;

        this.animations = {
            idle: { frames: this.loadFrames("Idle", 5), frameBuffer: 16 },
            run: { frames: this.loadFrames("Run", 5), frameBuffer: 10 },
            jump: { frames: this.loadFrames("Jump", 1), frameBuffer: 18 },
            fall: { frames: this.loadFrames("Fall", 1), frameBuffer: 18 },
            attack: { frames: this.loadFrames("Attack", 3), frameBuffer: 10 },
            air_attack: { frames: this.loadFrames("Air_Attack", 3), frameBuffer: 10 }
        };

        this.image = this.animations.idle.frames[0];
    }

    loadFrames(folder, count) {
        const frames = [];
        for (let i = 0; i < count; i++) {
            const img = new Image();
            img.src = `${this.basePath}/${folder}/${i}.png`;
            frames.push(img);
        }
        return frames;
    }

    setState(newState) {
        newState = newState.toLowerCase();
        if (this.state !== newState) {
            this.state = newState;
            this.elapsedFrames = 0;
            this.currentFrame = 0;
        }
    }

    attack() {
        if (!this.isAttacking) {
            this.isAttacking = true;

            if (this.isJumping) {
                this.setState("air_attack");
            } else {
                this.setState("attack");
            }
        }
    }

    jump() {
        if (!this.isJumping) {
            this.velocity.y = -18;
            this.isJumping = true;
            this.setState("jump");
        }
    }

    applyGravity(keys, platforms = []) {
        this.velocity.y += this.gravity;
        this.position.y += this.velocity.y;

        const playerHeight = this.image?.height || 0;
        const playerWidth = this.image?.width || 0;

        const feetOffset = 18;
        const headOffset = 22;
        const sideLeft = 34;
        const sideRight = 2;

        let onPlatform = false;

        for (const platform of platforms) {
            const playerLeft = this.position.x + sideLeft;
            const playerRight = this.position.x + playerWidth - sideRight;
            const playerTop = this.position.y + headOffset;
            const playerBottom = this.position.y + playerHeight - feetOffset;

            const platLeft = platform.position.x;
            const platRight = platform.position.x + platform.width;
            const platTop = platform.position.y;
            const platBottom = platform.position.y + platform.height;

            const horizontallyOverlapping = playerRight > platLeft && playerLeft < platRight;

            if (this.prevY + playerHeight - feetOffset <= platTop &&
                playerBottom >= platTop &&
                this.velocity.y >= 0 &&
                horizontallyOverlapping) {

                this.velocity.y = 0;
                this.position.y = platTop - playerHeight + feetOffset;
                this.isJumping = false;
                onPlatform = true;
                break;
            }

            if (this.prevY + headOffset >= platBottom &&
                playerTop <= platBottom &&
                this.velocity.y < 0 &&
                horizontallyOverlapping) {
                this.velocity.y = 0;
                this.position.y = platBottom - headOffset;
            }

            if (playerRight > platLeft &&
                this.prevX + playerWidth - sideRight <= platLeft &&
                playerBottom > platTop &&
                playerTop < platBottom) {
                this.position.x = platLeft - playerWidth + sideRight;
            }

            if (playerLeft < platRight &&
                this.prevX + sideLeft >= platRight &&
                playerBottom > platTop &&
                playerTop < platBottom) {
                this.position.x = platRight - sideLeft;
            }
        }

        const groundLevel = canvas.height - playerHeight + feetOffset;
        if (!onPlatform && this.position.y > groundLevel) {
            this.position.y = groundLevel;
            this.velocity.y = 0;
            this.isJumping = false;
            onPlatform = true;
        }

        if (this.isAttacking) return; // Attack in progress
        if (!onPlatform && this.velocity.y < 0) {
            this.setState("jump");
            this.isJumping = true;
        } else if (!onPlatform && this.velocity.y > 0) {
            this.setState("fall");
            this.isJumping = true;
        } else if (onPlatform) {
            const movingHorizontally = keys.d?.pressed || keys.a?.pressed;
            this.isJumping = false;
            this.setState(movingHorizontally ? "run" : "idle");
        }
    }

    moveHorizontal(keys) {
        if (keys.d?.pressed) {
            this.position.x += this.speed;
            this.facing = "Right";
        } else if (keys.a?.pressed) {
            this.position.x -= this.speed;
            this.facing = "Left";
        }
    }

    updateFrame() {
        this.elapsedFrames++;

        const currentAnim = this.animations[this.state];
        const frameBuffer = currentAnim.frameBuffer;
        const frames = currentAnim.frames;

        if (this.elapsedFrames % frameBuffer === 0) {
            this.currentFrame = (this.currentFrame + 1) % frames.length;
            this.image = frames[this.currentFrame];

            if ((this.state === "attack" || this.state === "air_attack") &&
                this.currentFrame === frames.length - 1) {
                this.isAttacking = false;
            }
        }
    }

    update(keys, platforms) {
        this.prevY = this.position.y;
        this.prevX = this.position.x;
        this.moveHorizontal(keys);
        this.applyGravity(keys, platforms);
        this.updateFrame();
    }

    draw() {
        ctx.save();

        if (this.facing === "Left") {
            ctx.scale(-1, 1);
            ctx.drawImage(this.image, -this.position.x - this.image.width, this.position.y);
        } else {
            ctx.drawImage(this.image, this.position.x, this.position.y);
        }

        ctx.restore();
    }
}