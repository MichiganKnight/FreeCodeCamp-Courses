import express from "express"
import dotenv from "dotenv"
import { connectDB } from "./Config/DB.js"
import Product from "./Models/Product.js"

dotenv.config()

const app = express()

app.use(express.json()) // Allows JSON Data in req.body

app.post("/API/Products", async (req, res) => {
    const product = req.body // User Sends Data

    if (!product.name || !product.price || !product.image) {
        return res.status(400).json({
            success: false,
            message: "Please Provide All Fields"
        })
    }

    const newProduct = new Product(product)

    try {
        await newProduct.save()

        res.status(201).json({
            success: true,
            data: newProduct
        })
    } catch (error) {
        console.log(`Error Creating Product: ${error.message}`)

        res.status(500).json({
            sucess: false,
            message: "Server Error"
        })
    }
})

// Postman Desktop App

app.listen(5000, () => {
    connectDB()

    console.log("Server Started at http://localhost:5000")
})