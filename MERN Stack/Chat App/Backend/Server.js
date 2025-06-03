import express from "express"
import dotenv from "dotenv"

import authRoutes from "./Routes/Auth.js"
import connectToMongoDB from "./DB/ConnectToDB.js"

dotenv.config()

const app = express()
const PORT = process.env.PORT || 5000

app.get("/", (req, res) => {
    // Root Route - http://localhost:5000
    res.send("Hello World")
})

app.use("/API/Auth", authRoutes)

app.listen(PORT, () => {
    connectToMongoDB()

    console.log(`Server Running on Port: ${PORT}`)
})