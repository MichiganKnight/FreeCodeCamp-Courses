import express from "express"
import { getMessages, sendMessage } from "../Controllers/Message.js"
import protectRoute from "../Middleware/ProtectRoute.js"

const router = express.Router()

router.get("/:id", protectRoute, getMessages)

router.post("/Send/:id", protectRoute, sendMessage)

export default router