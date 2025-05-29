const path = require('path')

console.log(path.sep)

const filePath = path.join('/Content', 'Subfolder', 'Test.txt')
console.log(filePath)

const base = path.basename(filePath)
console.log(base)

const absolutePath = path.resolve(__dirname, 'Content', 'Subfolder', 'Test.txt')
console.log(absolutePath)