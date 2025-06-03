import express from "express"
import dotenv from "dotenv"

import authRoutes from "./Routes/Auth.js"
import connectToMongoDB from "./DB/ConnectToDB.js"

const app = express()
const PORT = process.env.PORT || 5000

dotenv.config()

app.use(express.json()) // Pase Incoming Requests with JSON
app.use("/API/Auth", authRoutes)

/* app.get("/", (req, res) => {
    // Root Route - http://localhost:5000
    res.send("Hello World")
}) */

app.listen(PORT, () => {
    connectToMongoDB()

    console.log(`Server Running on Port: ${PORT}`)
})