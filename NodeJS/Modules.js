// CommonJS - Every file is module (By Default)
// Modules - Encapsulated Code (Only Share Minimum)

const names = require('./Names')
const sayHi = require('./Utils')

sayHi('Drew')
sayHi(names.john)
sayHi(names.peter)