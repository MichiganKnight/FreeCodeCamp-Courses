import express from "express"
import dotenv from "dotenv"
import path from "path"
import { connectDB } from "./Config/DB.js"
import productRoutes from "./Routes/Product.js"

dotenv.config()

const app = express()
const PORT = process.env.PORT || 5000

const __dirname = path.resolve()

app.use(express.json()) // Allows JSON Data in req.body
app.use("/API/Products", productRoutes)

if (process.env.NODE_ENV === "production") {
    app.use(express.static(path.join(__dirname, "/Frontend/dist")))
    app.get("*", (req, res) => {
        res.sendFile(path.resolve(__dirname, "Frontend", "dist", "index.html"))
    })
}

app.listen(PORT, () => {
    connectDB()

    console.log(`Server Started at http://localhost:${PORT}`)
})