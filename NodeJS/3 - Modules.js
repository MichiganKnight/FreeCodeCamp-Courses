// CommonJS - Every file is module (By Default)
// Modules - Encapsulated Code (Only Share Minimum)

const names = require('./4 - Names')
const sayHi = require('./5 - Utils')
const data = require('./6 - Alternative Syntax')

require('./7 - Mind Grenade')

/* console.log(data)
console.log(data.items)
console.log(data.items[0] + ' ' + data.items[1])
console.log(data.singlePerson.name)

sayHi('Drew')
sayHi(names.john)
sayHi(names.peter) */