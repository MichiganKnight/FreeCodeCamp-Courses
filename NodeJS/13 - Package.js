// npm --version

// Local Dependency
// npm install <packageName>

// Global Dependency
// npm install -g <packageName>

// package.json - Manifest File
// Manual Approach (Create package.json in Root)

// npm init    (Step by Step)
// npm init -y (Default)

const _ = require('lodash')

const items = [1, [2, [3, [4]]]]
const newItems = _.flattenDeep(items)

console.log(newItems)