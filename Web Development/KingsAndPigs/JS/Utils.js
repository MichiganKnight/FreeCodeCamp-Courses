import {CollisionBlock} from "./Classes/CollisionBlock.js";
import {collisionBlocks, ctx} from "./Index.js";

export function parse2D(array, rowLength = 16) {
    const rows = [];

    for (let i = 0; i < array.length; i += rowLength) {
        rows.push(array.slice(i, i + rowLength));
    }

    return rows;
}

export function createObjectsFrom2D(array) {
    const objects = [];

    array.forEach((row, y) => {
        row.forEach((symbol, x) => {
            if (symbol === 292) {
                objects.push(new CollisionBlock({
                    position: {
                        x: x * 64,
                        y: y * 64
                    },
                    ctx
                }));
            }
        });
    });

    return objects;
}