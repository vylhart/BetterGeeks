package com.example.bettergeeks.data.model.remote


data class ChatGptRequest(
    val model: String,
    val messages: List<Message>,
)