import bcrypt from "bcryptjs"

import User from "../Models/User.js"
import generateTokenAndSetCookie from "../Utils/GenerateToken.js"

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
        const salt = await bcrypt.genSalt(10)
        const hashedPassword = await bcrypt.hash(password, salt)

        const maleProfilePic = `https://avatar.iran.liara.run/public/boy?username=${username}`
        const femaleProfilePic = `https://avatar.iran.liara.run/public/girl?username=${username}`

        const newUser = new User({
            fullName,
            username,
            password: hashedPassword,
            gender,
            profilePic: gender === "male" ? maleProfilePic : femaleProfilePic
        })

        if (newUser) {
            // Generate JWT Token
            generateTokenAndSetCookie(newUser._id, res)
            await newUser.save()

            res.status(201).json({
                _id: newUser._id,
                fullName: newUser.fullName,
                username: newUser.username,
                profilePic: newUser.profilePic
            })
        } else {
            res.status(400).json({
                error: "Invalid User Data"
            })
        }
    } catch (error) {
        console.log(`Error During Signup: ${error.message}`)

        res.status(500).json({
            error: "Internal Server Error"
        })
    }
}

export const login = async (req, res) => {
    try {
        const { username, password } = req.body
        const user = await User.findOne({ username })
        const isPasswordCorrect = await bcrypt.compare(password, user?.password || "")

        if (!user || !isPasswordCorrect) {
            return res.status(400).json({
                error: "Invalid Username or Password"
            })
        }

        generateTokenAndSetCookie(user._id, res)

        res.status(200).json({
            _id: user._id,
            fullName: user.fullName,
            username: user.username,
            profilePic: user.profilePic
        })
    } catch (error) {
        console.log(`Error Logging In: ${error.message}`)

        res.status(500).json({
            error: "Internal Server Error"
        })
    }
}

export const logout = (req, res) => {
    try {
        res.cookie("jwt", "", { maxAge: 0 })

        res.status(200).json({
            message: "Logged Out Successfully"
        })
    } catch (error) {
        console.log(`Error Logging Out: ${error.message}`)

        res.status(500).json({
            error: "Internal Server Error"
        })
    }
}