import { canvas, ctx } from "../Index.js";

export class Player {
    constructor({ position, imageSrc }) {
        this.position = position;
        this.velocity = { x: 0, y: 0 };
        this.speed = 5;
        this.gravity = 0.8;
        this.isJumping = false;
        this.prevY = this.position.y;

        this.state = "idle";
        this.frameBuffer = 6;
        this.elapsedFrames = 0;
        this.currentFrame = 0;

        this.facing = "Right";
        this.basePath = imageSrc;

        this.animations = {
            idle: { frames: this.loadFrames("Idle", 5), frameBuffer: 16 },
            run: { frames: this.loadFrames("Run", 5), frameBuffer: 10 },
            jump: { frames: this.loadFrames("Jump", 1), frameBuffer: 18 }
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

    jump() {
        if (!this.isJumping) {
            this.velocity.y = -18;
            this.isJumping = true;
            this.setState("Jump");
        }
    }

    applyGravity(platforms = []) {
        this.velocity.y += this.gravity;
        this.position.y += this.velocity.y;

        const playerHeight = this.image?.height || 0;
        const playerWidth = this.image?.width || 0;

        const feetOffset = 18;
        const headOffset = 22;
        const leftOffset = 34;
        const rightOffset = 2;

        let onPlatform = false;

        for (const platform of platforms) {
            const offsetLeft = this.facing === "Left" ? rightOffset : leftOffset;
            const offsetRight = this.facing === "Left" ? leftOffset : rightOffset;

            const withinX =
                this.position.x + playerWidth - offsetRight > platform.position.x &&
                this.position.x + offsetLeft < platform.position.x + platform.width;

            const fallingOnto =
                this.prevY + playerHeight - feetOffset <= platform.position.y &&
                this.position.y + playerHeight - feetOffset >= platform.position.y &&
                this.velocity.y >= 0;

            if (withinX && fallingOnto) {
                this.velocity.y = 0;
                this.position.y = platform.position.y - playerHeight + feetOffset;
                this.isJumping = false;
                onPlatform = true;
                break;
            }
        }

        const groundLevel = canvas.height - playerHeight + feetOffset;
        if (!onPlatform && this.position.y > groundLevel) {
            this.position.y = groundLevel;
            this.velocity.y = 0;
            this.isJumping = false;
        }

        if (onPlatform) {
            const platform = platforms.find(p => {
                const offsetLeft = this.facing === "Left" ? rightOffset : leftOffset;
                const offsetRight = this.facing === "Left" ? leftOffset : rightOffset;
                return (
                    this.position.x + playerWidth - offsetRight > p.position.x &&
                    this.position.x + offsetLeft < p.position.x + p.width
                );
            });

            if (!platform) {
                this.isJumping = true;
            }
        }
    }

    moveHorizontal(keys) {
        if (keys.d.pressed) {
            this.position.x += this.speed;
            this.facing = "Right";
            if (!this.isJumping) this.setState("Run");
        } else if (keys.a.pressed) {
            this.position.x -= this.speed;
            this.facing = "Left";
            if (!this.isJumping) this.setState("Run");
        } else if (!this.isJumping) {
            this.setState("Idle");
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
        }
    }

    update(keys, platforms) {
        this.prevY = this.position.y;
        this.moveHorizontal(keys);
        this.applyGravity(platforms);
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