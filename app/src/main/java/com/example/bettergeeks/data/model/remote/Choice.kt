package com.example.bettergeeks.data.model.remote


data class Choice(
    val index: Int,
    val message: Message,
    val finish_reason: String,
)