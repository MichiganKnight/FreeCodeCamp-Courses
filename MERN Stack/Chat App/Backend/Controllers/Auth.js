import User from "../Models/User.js"

export const signup = async (req, res) => {
    try {
        const { fullName, username, password, confirmPassword, gender } = req.body

        if (password !== confirmPassword) {
            return res.status(400).json({
                error: "Passords Don't Match"
            })
        }

        const user = await User.findOne({ username })

        if (user) {
            return res.status(400).json({
                error: "Username Already Exists"
            })
        }

        // HASH PASSWORD HERE

        const maleProfilePic = `https://avatar.iran.liara.run/public/boy?username=${username}`
        const femaleProfilePic = `https://avatar.iran.liara.run/public/girl?username=${username}`

        const newUser = new User({
            fullName,
            username,
            password,
            gender,
            profilePic: gender === "male" ? maleProfilePic : femaleProfilePic
        })

        await newUser.save()

        res.status(201).json({
            _id: newUser._id,
            fullName: newUser.fullName,
            username: newUser.username,
            profilePic: newUser.profilePic
        })
    } catch (error) {
        console.log(`Error During Signup: ${error.message}`)
        
        res.status(500).json({
            error: "Internal Server Error"
        })
    }
}

export const login = async (req, res) => {

}

export const logout = async (req, res) => {

}