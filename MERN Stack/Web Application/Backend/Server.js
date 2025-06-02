import express from "express"
import dotenv from "dotenv"
import { connectDB } from "./Config/DB.js"

dotenv.config()

const app = express()

app.get("/Products", (req, res) => {
    
})

app.listen(5000, () => {
    connectDB()

    console.log("Server Started at http://localhost:5000")
})