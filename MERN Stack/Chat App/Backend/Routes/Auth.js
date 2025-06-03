import express from "express"
import { login, logout, signup } from "../Controllers/Auth.js"

const router = express.Router()

router.post("/Signup", signup)

router.post("/Login", login)

router.post("/Logout", logout)

export default router