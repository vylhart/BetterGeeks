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

    suspend fun generateAnswer(question: String): ResponseData<String> =
        withContext(Dispatchers.IO) {
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
        return@withContext ResponseData.Success("https://oaidalleapiprodscus.blob.core.windows.net/private/org-3ey5GniJkme0tNoQRcAVdfwg/user-J8yXXqh1I5GcrsvKIuphRjkm/img-bEwXmH2AjtnqQDNQZMWipmYF.png?st=2023-06-11T08%3A55%3A05Z&se=2023-06-11T10%3A55%3A05Z&sp=r&sv=2021-08-06&sr=b&rscd=inline&rsct=image/png&skoid=6aaadede-4fb3-4698-a8f6-684d7786b067&sktid=a48cca56-e6da-484e-a814-9c849652bcb3&skt=2023-06-10T20%3A48%3A06Z&ske=2023-06-11T20%3A48%3A06Z&sks=b&skv=2021-08-06&sig=AUvkeanZlyY9CLyyMF0AQIG/C9KSdR2mbDJgu31%2BqPw%3D")

//        val response = openAiService.generateImage(request)
//
//        Log.i(Common.TAG, "generateImageFromText: $response")
//
//        if (response.isSuccessful) {
//            response.body()?.let {
//                if (it.images.isNotEmpty()) {
//                    return@withContext ResponseData.Success(it.images[0].imageUrl)
//                }
//            }
//            return@withContext ResponseData.Error("Empty response or missing choice.")
//        } else {
//            return@withContext ResponseData.Error("API request failed: ${response.code()}")
//        }
    }
}