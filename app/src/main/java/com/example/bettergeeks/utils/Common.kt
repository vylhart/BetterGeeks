package com.example.bettergeeks.utils

import com.example.bettergeeks.R
import com.example.bettergeeks.data.model.remote.ChatGptRequest
import com.example.bettergeeks.data.model.remote.Message

class Common {

    companion object {
        const val TAG = "BetterGeeks# : "
        const val KEY_TOPIC_ID = "topic_id"
        const val KEY_QUESTION_ID = "question_id"
        private val colorList = listOf(
            R.color.green,
            R.color.blue,
            R.color.purple,
            R.color.yellow,
            R.color.orange,
            R.color.pink
        )

        fun getColor(position: Int): Int {
            return colorList[position % colorList.size]
        }

        const val API_KEY = "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"
        const val URL = "https://api.openai.com/v1/chat/completions"

        fun getChatGptRequest(question: String) = ChatGptRequest(
            model = "gpt-3.5-turbo",
            messages = listOf(Message(role = "user", content = question))
        )


    }
}