import {canvas, ctx} from '../Index.js';

export class Sprite {
    constructor({position, imageSrc, frameRate = 1}) {
        this.position = position;
        this.loaded = false;
        this.image = new Image();
        this.image.src = imageSrc;

        this.image.onload = () => {
            this.loaded = true;
            this.width = this.image.width / this.frameRate;
            this.height = this.image.height;
        };

        this.frameRate = frameRate;
    }

    draw() {
        if (!this.loaded) {
            return;
        }

        const cropBox = {
            position: {
                x: 0,
                y: 0
            },
            width: this.width,
            height: this.height
        }

        ctx.drawImage(this.image, cropBox.position.x, cropBox.position.y, cropBox.width, cropBox.height, this.position.x, this.position.y, this.width, this.height);
    }
}