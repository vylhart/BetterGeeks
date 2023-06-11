package com.example.bettergeeks.data.dao.remote

import android.util.Log
import com.example.bettergeeks.data.model.remote.ChatGptRequest
import com.example.bettergeeks.data.model.remote.ImageGenerationRequest
import com.example.bettergeeks.data.model.remote.Message
import com.example.bettergeeks.screens.ask_question.ResponseData
import com.example.bettergeeks.utils.Common
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class OpenAiRepository @Inject constructor(private val openAiService: OpenAiService) {

    private fun getChatGptRequest(question: String) = ChatGptRequest(
        model = "gpt-3.5-turbo",
        messages = listOf(Message(role = "user", content = question))
    )

    suspend fun generateAnswer(question: String): ResponseData<String> = withContext(Dispatchers.IO) {
            val request = getChatGptRequest(question)
            val response = openAiService.generateCompletion(request)

            Log.i(Common.TAG, "generateAnswer: $response")

            if (response.isSuccessful) {
                response.body()?.let {
                    if (it.choices.isNotEmpty()) {
                        return@withContext ResponseData.Success(it.choices[0].message.content)
                    }
                }
                return@withContext ResponseData.Error("Empty response or missing choice.")
            } else {
                return@withContext ResponseData.Error("API request failed: ${response.code()}")
            }
        }


    suspend fun generateImageFromText(text: String) = withContext(Dispatchers.IO) {
        val request = ImageGenerationRequest(text, 1, "512x512")
        val response = openAiService.generateImage(request)

        Log.i(Common.TAG, "generateImageFromText: $response")

        if (response.isSuccessful) {
            response.body()?.let {
                if (it.images.isNotEmpty()) {
                    return@withContext ResponseData.Success(it.images[0].imageUrl)
                }
            }
            return@withContext ResponseData.Error("Empty response or missing choice.")
        } else {
            return@withContext ResponseData.Error("API request failed: ${response.code()}")
        }
    }
}