export class Entity {
    constructor({
                    position,
                    speed = 0,
                    gravity = 0,
                    imageSrc = "",
                    animations = {},
                    initialState = "idle",
                    frameBuffer = 6,
                    facing = "Right"
                }) {
        this.position = {...position}
        this.velocity = {x: 0, y: 0}
        this.speed = speed
        this.gravity = gravity

        this.state = initialState;
        this.frameBuffer = frameBuffer;
        this.elapsedFrames = 0;
        this.currentFrame = 0;

        this.facing = facing;
        this.basePath = imageSrc;
        this.animations = animations;

        this.image = this.animations[this.state]?.frames?.[0] ?? null;
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

    updateFrame() {
        this.elapsedFrames++;
        const currentAnim = this.animations[this.state];
        if (!currentAnim) return;

        const {frameBuffer, frames} = currentAnim;
        if (!frames || frames.length === 0) return;

        if (this.elapsedFrames % frameBuffer === 0) {
            this.currentFrame = (this.currentFrame + 1) % frames.length;
            this.image = frames[this.currentFrame];
        }
    }

    get width() {
        return this.image?.width || 0;
    }

    get height() {
        return this.image?.height || 0;
    }
}