// Partial Types
interface Sticker {
    id: number;
    name: string;
    createdAt: string;
    updatedAt: string;
    submitter: unknown | string;
}

type StickerUpdateParams = Partial<Sticker>;

// Readonly Types
type StickerFromAPI = Readonly<Sticker>;

// Record Types
type NavigationPages = "home" | "stickers" | "about" | "contact";

interface PageInfo {
    title: string;
    url: string;
    axTitle?: string;
}

const navigationInfo: Record<NavigationPages, PageInfo> = {
    home: {
        title: "Home",
        url: "/"
    },
    about: {
        title: "About",
        url: "/about"
    },
    contact: {
        title: "Contact",
        url: "/contact"
    },
    stickers: {
        title: "Stickers",
        url: "/stickers/all"
    }
}

// Pick Types
type StickerSortPreview = Pick<Sticker, "name" | "updatedAt">

// Omit Types
type StickerTimeMetadata = Omit<Sticker, "name">

// Exclude Types
type HomeNavigationPages = Exclude<NavigationPages, "home">