export const sendMessage = async (req, res) => {
    try {
        
    } catch (error) {
        console.log(`Error In Message: ${error.message}`)   

        res.status(500).json({
            error: "Internal Server Error"
        })
    }
}