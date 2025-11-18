import { Platform } from "./Classes/Platform.js";

// javascript
export async function fetchMapJson(url) {
    const res = await fetch(url);
    if (!res.ok) throw new Error(`Failed to fetch map: ${res.status} ${res.statusText}`);
    return await res.json(); // important: parse as JSON
}

export function loadTilesetsFromMap(map) {
    if (!map) throw new TypeError('map is required');
    // normalize tilesets to an array (handles object or missing)
    const raw = map.tilesets ?? [];
    const tilesets = Array.isArray(raw) ? raw : [raw];

    return tilesets.map(ts => ({
        firstgid: ts.firstgid ?? 1,
        name: ts.name ?? '',
        source: ts.source ?? ts.tileset ?? ts.image ?? ''
    }));
}

export function normalizeLayerData(layer) {
    if (!layer || !layer.data) return [];
    return layer.data.map(cell => {
        if (cell === 0) return 0;
        if (typeof cell === 'number') return cell;
        if (cell && typeof cell === 'object') return cell.gid ?? 0;
        return 0;
    });
}

/* Usage example (in `JS/Game/Game.js`):
   const map = await fetchMapJson('/Assets/Level/yourmap.json');
   const tilesets = loadTilesetsFromMap(map);
   const groundLayer = map.layers.find(l => l.name === 'Ground');
   const data = normalizeLayerData(groundLayer);
*/