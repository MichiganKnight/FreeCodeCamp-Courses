import express from "express"

const app = express();

app.get("/Products", (req, res) => {
    
})

app.listen(5000, () => {
    console.log("Server Started at http://localhost:5000")
})