import express from "express"
import dotenv from "dotenv"
import { connectDB } from "./Config/DB.js"
import productRoutes from "./Routes/Product.js"

dotenv.config()

const app = express()

app.use(express.json()) // Allows JSON Data in req.body
app.use("/API/Products", productRoutes)

app.listen(5000, () => {
    connectDB()

    console.log("Server Started at http://localhost:5000")
})