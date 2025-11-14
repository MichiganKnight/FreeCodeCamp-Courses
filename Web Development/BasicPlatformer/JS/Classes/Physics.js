// JS/Classes/Physics.js

/**
 * @typedef {Object} ColliderOffsets
 * @property {number} top
 * @property {number} bottom
 * @property {number} left
 * @property {number} right
 */

/**
 * Applies gravity and resolves collisions with platforms and ground.
 *
 * @param {Entity} entity - The moving entity (must have position, velocity, width, height, gravity, facing).
 * @param {Array<Platform>} platforms - Platforms to collide with.
 * @param {number} groundLevel - Y coordinate of the ground (e.g., level.height).
 * @param {ColliderOffsets} collider - Collision box offsets from the sprite's edges.
 * @returns {boolean} onGround - True if standing on ground or a platform.
 */
export function applyGravityAndCollisions(entity, platforms, groundLevel, collider) {
    const {top, bottom, left, right} = collider;

    // --- Apply gravity ---
    entity.velocity.y += entity.gravity;
    if (entity.velocity.y > 15) entity.velocity.y = 15; // max fall speed

    const playerWidth = entity.width;
    const playerHeight = entity.height;

    let nextY = entity.position.y + entity.velocity.y;
    let onPlatformOrGround = false;

    // --- PLATFORM COLLISIONS ---
    for (const platform of platforms) {
        const platTop = platform.position.y;
        const platBottom = platform.position.y + platform.height;
        const platLeft = platform.position.x;
        const platRight = platLeft + platform.width;

        const playerLeft = entity.position.x + left;
        const playerRight = entity.position.x + playerWidth - right;
        const playerTop = entity.position.y + top;
        const playerBottom = entity.position.y + playerHeight - bottom;

        const horizontallyOverlapping =
            playerRight > platLeft && playerLeft < platRight;

        // LAND ON PLATFORM (from above)
        if (
            horizontallyOverlapping &&
            entity.velocity.y >= 0 &&
            playerBottom <= platTop &&
            nextY + playerHeight - bottom >= platTop
        ) {
            entity.velocity.y = 0;
            entity.position.y = platTop - (playerHeight - bottom);
            onPlatformOrGround = true;
        }

        // HEAD HIT (jumping into platform from below)
        if (
            horizontallyOverlapping &&
            entity.velocity.y < 0 &&
            playerTop >= platBottom &&
            nextY + top <= platBottom
        ) {
            entity.velocity.y = 0;
            entity.position.y = platBottom - top;
        }

        // SIDE COLLISIONS
        const nextLeft = entity.position.x + entity.velocity.x + left;
        const nextRight = entity.position.x + entity.velocity.x + playerWidth - right;

        // LEFT SIDE (moving left into platform)
        if (
            playerBottom > platTop &&
            playerTop < platBottom &&
            entity.velocity.x < 0 &&
            nextLeft <= platRight &&
            playerLeft >= platRight
        ) {
            entity.velocity.x = 0;
            entity.position.x = platRight - left;
        }

        // RIGHT SIDE (moving right into platform)
        if (
            playerBottom > platTop &&
            playerTop < platBottom &&
            entity.velocity.x > 0 &&
            nextRight >= platLeft &&
            playerRight <= platLeft
        ) {
            entity.velocity.x = 0;
            entity.position.x = platLeft - playerWidth + right;
        }
    }

    // --- GROUND COLLISION ---
    const playerBottom = entity.position.y + playerHeight - bottom;
    const nextBottom = nextY + playerHeight - bottom;

    if (
        !onPlatformOrGround &&
        entity.velocity.y >= 0 &&         // only when falling / standing
        playerBottom <= groundLevel &&    // currently above or on ground
        nextBottom >= groundLevel         // next step would cross ground
    ) {
        entity.velocity.y = 0;
        entity.position.y = groundLevel - (playerHeight - bottom);
        onPlatformOrGround = true;
    }

    // --- APPLY FINAL VERTICAL POSITION ---
    entity.position.y += entity.velocity.y;

    return onPlatformOrGround;
}