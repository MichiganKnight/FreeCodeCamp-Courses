const os = require('os')

// Info About Current User
const user = os.userInfo()
console.log(user)

// Method Returns System Uptime in Seconds
console.log(`System Uptime: ${os.uptime()} Seconds`)

const currentOS = {
    name: os.type(),
    release: os.release(),
    totalMemory: os.totalmem(),
    freeMem: os.freemem()
}

console.log(currentOS)