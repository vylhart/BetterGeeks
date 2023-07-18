package com.kudesta.bettergeeks.data.model.remote

import com.google.gson.annotations.SerializedName

data class ChatGptResponse(
    val id: String,
    val `object`: String,
    val created: Long,
    val choices: List<Choice>,
    val usage: Usage,
)

data class ImageGenerationRequest(
    @SerializedName("prompt") val prompt: String,
    @SerializedName("n") val count: Int,
    @SerializedName("size") val size: String
)

data class ImageGenerationResponse(
    @SerializedName("created") val created: Long,
    @SerializedName("data") val images: List<ImageData>
)

data class ImageData(
    @SerializedName("url") val imageUrl: String
)