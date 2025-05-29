const { readFile, writeFile } = require('fs')

readFile('./Content/First.txt', 'utf8', (err, result) => {
    if (err) {
        console.log(err)

        return
    }

    const first = result

    readFile('./Content/Second.txt', 'utf8', (err, result) => {
        if (err) {
            console.log(err)

            return
        }

        const second = result

        writeFile('./Content/Result-Async.txt', `Result: ${first}, ${second}`, (err, result) => {
            if (err) {
                console.log(err)

                return
            }

            console.log(result)
        })
    })
})