const http = require('http')

const server = http.createServer((req, res) => {
    if (req.url === '/') {
        res.end('Welcome to Our Home Page')

        return
    }

    if (req.url === '/About') {
        res.end('About Page')

        return
    }

    res.end(`<h1>404</h1><p>Page Not Found</p><a href="/">Home Page</a>`)

    return
})

server.listen(5000)