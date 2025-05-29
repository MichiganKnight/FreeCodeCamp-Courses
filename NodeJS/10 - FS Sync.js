const {readFileSync, writeFileSync} = require('fs')

const first = readFileSync('./Content/First.txt', 'utf8')
const second = readFileSync('./Content/Second.txt', 'utf8')

console.log(first, second)

writeFileSync('./Content/Result-Sync.txt', `Result: ${first}, ${second}`, {flag: 'a'})