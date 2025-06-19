console.log("=== Import / Export ===");

import subtract, { capitalizeString } from "./stringFunction.js"; // .js Assumed

const cap = capitalizeString("Hello!");

console.log(cap);

//import * as capitalizeStrings from "stringFunction";

console.log(subtract(5, 4));