package com.example.bettergeeks.data.model.remote

data class ChatGptResponse(
    val id: String,
    val `object`: String,
    val created: Long,
    val choices: List<Choice>,
    val usage: Usage,
)