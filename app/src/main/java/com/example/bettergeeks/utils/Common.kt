package com.example.bettergeeks.utils

import com.example.bettergeeks.R

object Common {

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

    fun randomColor(): Int {
        return colorList.random()
    }


    fun parseQuestionList(text: String): MutableList<String> {
        val regex = Regex("\\d+\\.\\s.*")
        val questionsList: MutableList<String> = mutableListOf()

        regex.findAll(text).forEach { matchResult ->
            val question = matchResult.value
            questionsList.add(question)
        }
        return questionsList
    }

}