import Conversation from "../Models/Conversation.js"
import Message from "../Models/Message.js"

export const sendMessage = async (req, res) => {
    try {
        const { message } = req.body
        const { id: receiverId } = req.params
        const senderId = req.userId

        let conversation = await Conversation.findOne({
            participants: { $all: [senderId, receiverId] }
        })

        if (!conversation) {
            conversation = await Conversation.create({
                participants: { $all: [senderId, receiverId] }
            })
        }

        const newMessage = new Message({
            senderId,
            receiverId,
            message
        })

        if (newMessage) {
            conversation.messages.push(newMessage._id)
        }

        res.status(201).json({ newMessage })
    } catch (error) {
        console.log(`Error In Message: ${error.message}`)

        res.status(500).json({
            error: "Internal Server Error"
        })
    }
}