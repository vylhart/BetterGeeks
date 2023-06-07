package com.example.bettergeeks.data.model.remote

data class Usage(
    val prompt_tokens: Int,
    val completion_tokens: Int,
    val total_tokens: Int,
)