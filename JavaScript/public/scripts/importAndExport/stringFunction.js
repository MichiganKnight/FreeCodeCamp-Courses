export const capitalizeString = str => str.toUpperCase();

const capString = (string) => {
    return string.charAt(0).toUpperCase() + string.slice(1);
}

export { capString };

export const foo = "bar";
export const bar = "foo";

export default function subtract(x, y) { return x - y; }