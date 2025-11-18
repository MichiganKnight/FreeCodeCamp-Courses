import { tilesetSelect, tileSize } from "./Variables.js";
import { playerObject, tiles, levelWidth, levelHeight } from "./Variables.js";

/**
 * Build a JSON map for the editor/game
 */
export function buildLevelJson() {
    const map = {
        width: levelWidth,
        height: levelHeight,
        tilewidth: tileSize,
        tileheight: tileSize,
        layers: [],
        tilesets: [],
        /*orientation: "orthogonal",
        renderorder: "right-down",
        version: "1.9",
        type: "map",
        tiledversion: "1.9.0",
        infinite: false,
        nextlayerid: 3,
        nextobjectid: 2,
        layers: [],
        tilesets: []*/
    };

    const terrainData = tiles.slice();

    map.layers.push({
        id: 1,
        name: "Terrain",
        visible: true,
        opacity: 1,
        x: 0,
        y: 0,
        width: levelWidth,
        height: levelHeight,
        data: []
    });

    map.layers.push({
        id: 2,
        name: "Platforms",
        visible: true,
        opacity: 1,
        x: 0,
        y: 0,
        width: levelWidth,
        height: levelHeight,
        data: []
    });

    const objects = [];
    if (playerObject) {
        objects.push({
            id: 1,
            name: playerObject.name,
            type: playerObject.type,
            x: playerObject.x,
            y: playerObject.y,
            width: playerObject.width,
            height: playerObject.height
        });
    }

    map.layers.push({
        id: 2,
        name: "Objects",
        visible: true,
        opacity: 1,
        x: 0,
        y: 0,
        objects
    });

    let currentFirstGid = 1;
    for (const ts of tilesetSelect) {
        console.log(ts.tilecount);

        const tilecount = ts.tilecount || (ts.columns ? ts.columns * Math.floor((ts.imageHeight || 0) / ts.tileheight || 1) : 0);

        console.log(tilecount);

        map.tilesets.push({
            firstgid: currentFirstGid,
            source: ts.source || undefined,
            name: ts.name,
            tilewidth: ts.tilewidth,
            tileheight: ts.tileheight,
            image: ts.image,
            columns: ts.columns,
            tilecount: tilecount
        });

        currentFirstGid += Math.max(0, tilecount);
    }

    if (map.tilesets.length === 1) {
        const firstgid = map.tilesets[0].firstgid;
        map.layers[0].data = terrainData.map(v => {
            if (v === null || v === undefined || v === 0) return 0;
            if (v < firstgid) return v + firstgid;
            return v;
        });
    } else {
        map.layers[0].data = terrainData.map(v => (v === null || v === undefined ? 0 : v));
    }

    /*// Prepare terrain data (clone)
    const terrainData = tiles.slice(); // expected to be an array length width*height

    map.layers.push({
        id: 1,
        name: "Terrain",
        type: "tilelayer",
        visible: true,
        opacity: 1,
        x: 0,
        y: 0,
        width: levelWidth,
        height: levelHeight,
        data: [] // set below after tileset firstgid calculation
    });

    // Add player object layer
    const objects = [];
    if (playerObject) {
        objects.push({
            id: 1,
            name: playerObject.name,
            type: playerObject.type,
            x: playerObject.x,
            y: playerObject.y,
            width: playerObject.width,
            height: playerObject.height
        });
    }
    map.layers.push({
        id: 2,
        name: "Objects",
        type: "objectgroup",
        visible: true,
        opacity: 1,
        x: 0,
        y: 0,
        draworder: "topdown",
        objects
    });

    // Build tilesets with sequential firstgid (Tiled format)
    let currentFirstGid = 1;
    for (const ts of tilesetSelect) {
        const tilecount = ts.tilecount || (ts.columns ? ts.columns * Math.floor((ts.imageHeight || 0) / ts.tileheight || 1) : 0);

        map.tilesets.push({
            firstgid: currentFirstGid,
            source: ts.source || undefined,
            name: ts.name,
            tilewidth: ts.tilewidth,
            tileheight: ts.tileheight,
            image: ts.image,
            columns: ts.columns,
            tilecount: tilecount
        });

        currentFirstGid += Math.max(0, tilecount);
    }

    // Convert terrain data to global gids if necessary:
    // - If there is only one tileset and terrain values are local 0-based indices, add firstgid.
    // - Otherwise assume terrainData already contains global gids (0 means empty).
    if (map.tilesets.length === 1) {
        const firstgid = map.tilesets[0].firstgid;
        map.layers[0].data = terrainData.map(v => {
            if (v === null || v === undefined || v === 0) return 0;
            // if editor used 0-based local indices, convert to gids
            // if editor already used gids, ensure no double-conversion by checking range
            if (v < firstgid) return v + firstgid;
            return v;
        });
    } else {
        // multiple tilesets: assume editor produced global gids already; normalize null/undefined to 0
        map.layers[0].data = terrainData.map(v => (v === null || v === undefined ? 0 : v));
    }*/

    return map;
}