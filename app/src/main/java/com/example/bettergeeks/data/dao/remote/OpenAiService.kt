package com.example.bettergeeks.data.dao.remote

import com.example.bettergeeks.data.model.remote.ChatGptRequest
import com.example.bettergeeks.data.model.remote.ChatGptResponse
import com.example.bettergeeks.data.model.remote.ImageGenerationRequest
import com.example.bettergeeks.data.model.remote.ImageGenerationResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface OpenAiService {
    //@Header("Authorization") apiKey: String,

    @Headers("Content-Type: application/json")
    @POST("v1/chat/completions")
    suspend fun generateCompletion(@Body request: ChatGptRequest): Response<ChatGptResponse>

    @Headers("Content-Type: application/json")
    @POST("v1/images/generations")
    suspend fun generateImage(@Body request: ImageGenerationRequest): Response<ImageGenerationResponse>


}
