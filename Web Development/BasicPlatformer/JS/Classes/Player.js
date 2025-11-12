import { canvas, ctx, camera, level } from "../Index.js";

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
            this.setState(this.isJumping ? "air_attack" : "attack");
        }
    }

    jump() {
        // Only allow jump if grounded
        if (!this.isJumping) {
            this.velocity.y = -18;
            this.isJumping = true;
            this.setState("jump");
        }
    }

    applyGravity(keys, platforms = []) {
        this.prevY = this.position.y;
        this.prevX = this.position.x;

        this.velocity.y += this.gravity;
        this.position.y += this.velocity.y;

        const playerHeight = this.image?.height || 0;
        const playerWidth = this.image?.width || 0;

        const feetOffset = 18;
        const headOffset = 22;
        const sideLeft = this.facing === "Left" ? 2 : 34;
        const sideRight = this.facing === "Left" ? 34 : 2;

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

            // Land on top
            if (
                this.prevY + playerHeight - feetOffset <= platTop &&
                playerBottom >= platTop &&
                this.velocity.y >= 0 &&
                horizontallyOverlapping
            ) {
                this.position.y = platTop - playerHeight + feetOffset;
                this.velocity.y = 0;
                onPlatform = true;
                this.isJumping = false;
                break;
            }

            // Head hit
            if (
                this.prevY + headOffset >= platBottom &&
                playerTop <= platBottom &&
                this.velocity.y < 0 &&
                horizontallyOverlapping
            ) {
                this.position.y = platBottom - headOffset;
                this.velocity.y = 0;
            }

            // Side collisions
            if (
                playerRight > platLeft &&
                this.prevX + playerWidth - sideRight <= platLeft &&
                playerBottom > platTop &&
                playerTop < platBottom
            ) {
                this.position.x = platLeft - playerWidth + sideRight;
            }

            if (
                playerLeft < platRight &&
                this.prevX + sideLeft >= platRight &&
                playerBottom > platTop &&
                playerTop < platBottom
            ) {
                this.position.x = platRight - sideLeft;
            }
        }

        const groundLevel = canvas.height - playerHeight;
        if (!onPlatform && this.position.y > groundLevel) {
            this.position.y = groundLevel;
            this.velocity.y = 0;
            this.isJumping = false;
            onPlatform = true;
        }

// --- Animation states ---
        if (this.isAttacking) return;

        if (!onPlatform) {
            // only airborne if not snapped to ground
            this.isJumping = true;
            this.setState(this.velocity.y < 0 ? "jump" : "fall");
        } else {
            this.isJumping = false;
            const movingHorizontally = keys.d?.pressed || keys.a?.pressed;
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

            // End attack animation
            if ((this.state === "attack" || this.state === "air_attack") && this.currentFrame === frames.length - 1) {
                this.isAttacking = false;
            }
        }
    }

    update(keys, platforms) {
        this.prevY = this.position.y;
        this.prevX = this.position.x;

        // --- Movement and physics ---
        this.moveHorizontal(keys);
        this.applyGravity(keys, platforms);
        this.updateFrame();

        const playerWidth = this.image?.width || 0;
        const playerHeight = this.image?.height || 0;

        // --- CAMERA FOLLOW (smooth + clamped) ---
        const targetCameraX = this.position.x - canvas.width / 2 + playerWidth / 2;
        const targetCameraY = this.position.y - canvas.height / 2 + playerHeight / 2;
        const easing = 0.1;

        camera.x += (targetCameraX - camera.x) * easing;
        camera.y += (targetCameraY - camera.y) * easing;

        // Clamp camera within level bounds
        camera.x = Math.max(0, Math.min(camera.x, level.width - canvas.width));
        camera.y = Math.max(0, Math.min(camera.y, level.height - canvas.height));

        // --- PLAYER BOUNDARIES ---
        if (this.position.x < 0) this.position.x = 0;
        if (this.position.x + playerWidth > level.width)
            this.position.x = level.width - playerWidth;

        if (this.position.y + playerHeight > level.height) {
            this.position.y = level.height - playerHeight;
            this.velocity.y = 0;
            this.isJumping = false;
        }
    }

    draw() {
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
