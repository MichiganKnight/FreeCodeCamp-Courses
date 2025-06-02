import express from "express"
import mongoose from "mongoose"
import Product from "../Models/Product.js"

const router = express.Router()

router.get("/", async (req, res) => {
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

router.post("/", async (req, res) => {
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

router.put("/:id", async (req, res) => {
    const { id } = req.params

    const product = req.body

    if (!mongoose.Types.ObjectId.isValid(id)) {
        return res.status(404).json({
            success: false,
            message: "Product Not Found"
        })
    }

    try {
        const updatedProduct = await Product.findByIdAndUpdate(id, product, {
            new: true
        })

        res.status(200).json({
            success: true,
            data: updatedProduct
        })
    } catch (error) {
        console.log(`Error Updating Product: ${error.message}`)

        res.status(500).json({
            success: false,
            message: "Server Error"
        })
    }
})

router.delete("/:id", async (req, res) => {
    const { id } = req.params

    try {
        await Product.findByIdAndDelete(id)

        res.status(200).json({
            success: true,
            message: "Product Deleted"
        })
    } catch (error) {
        console.log(`Error Deleting Products: ${error.message}`)

        res.status(404).json({
            success: false,
            message: "Product Not Found"
        })
    }
})

export default router