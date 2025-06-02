import express from "express"
import dotenv from "dotenv"
import { connectDB } from "./Config/DB.js"
import Product from "./Models/Product.js"

dotenv.config()

const app = express()

app.use(express.json()) // Allows JSON Data in req.body

app.get("/API/Products", async (req, res) => {
    try {
        const products = await Product.find({})

        res.status(200).json({
            success: true,
            data: products
        })
    } catch (error) {
        console.log(`Error Fetching Products: ${error.message}`)

        res.status(500).json({
            success: false,
            message: "Server Error"
        })
    }
})

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

app.delete("/API/Products/:id", async (req, res) => {
    const { id } = req.params

    try {
        await Product.findByIdAndDelete(id)

        res.status(200).json({
            success: true,
            message: "Product Deleted"
        })
    } catch (error) {
        res.status(404).json({
            success: false,
            message: "Product Not Found"
        })
    }
})

// Postman Desktop App

app.listen(5000, () => {
    connectDB()

    console.log("Server Started at http://localhost:5000")
})