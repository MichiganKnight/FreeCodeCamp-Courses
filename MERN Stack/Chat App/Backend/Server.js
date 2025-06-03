const express = require("express")

const app = express()

app.get("/", (req, res) => {
    // Root Route - http://localhost:5000
    res.send("Hello World")
})

app.listen(5000, () => {
    console.log("Server Running on Port 5000")
})