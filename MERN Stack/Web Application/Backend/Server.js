import express from "express"
import dotenv from "dotenv"
import { connectDB } from "./Config/DB.js"
import productRoutes from "./Routes/Product.js"

dotenv.config()

const app = express()
const PORT = process.env.PORT || 5000

app.use(express.json()) // Allows JSON Data in req.body
app.use("/API/Products", productRoutes)

app.listen(PORT, () => {
    connectDB()

    console.log(`Server Started at http://localhost:${PORT}`)
})