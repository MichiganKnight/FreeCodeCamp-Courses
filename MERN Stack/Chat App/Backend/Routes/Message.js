import express from "express"
import { sendMessage } from "../Controllers/Message.js"
import protectRoute from "../Middleware/ProtectRoute.js"

const router = express.Router()

router.post("/Send/:id", protectRoute, sendMessage)

export default router