import { mutation, query } from "./_generated/server"
import { v } from "convex/values"

export const addComment = mutation({
    args: {
        interviewId: v.id("interviews"),
        content: v.string(),
        rating: v.number()
    },
    handler: async (ctx, args) => {
        const identiy = await ctx.auth.getUserIdentity()

        if (!identiy) {
            throw new Error("Unauthorized")
        }

        return await ctx.db.insert("comments", {
            interviewId: args.interviewId,
            content: args.content,
            rating: args.rating,
            interviewerId: identiy.subject
        })
    }
})

export const getComments = query({
    args: {
        interviewId: v.id("interviews")
    },
    handler: async (ctx, args) => {
        const comments = await ctx.db.query("comments").withIndex("by_interview_id", (q) => q.eq("interviewId", args.interviewId)).collect()

        return comments
    }
})