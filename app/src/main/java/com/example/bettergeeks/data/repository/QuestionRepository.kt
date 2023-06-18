package com.example.bettergeeks.data.repository

import com.example.bettergeeks.data.dao.local.QuestionDao
import com.example.bettergeeks.data.model.local.QuestionData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class QuestionRepository @Inject constructor(private val questionDao: QuestionDao) {

    fun getAllQuestions() = questionDao.getAllQuestions()

    fun getQuestionsByTopicId(topicId: String) = questionDao.getQuestionsByTopicId(topicId)

    fun getQuestionById(id: Int) = questionDao.getQuestionById(id)

    suspend fun insertQuestion(questionData: QuestionData) = withContext(Dispatchers.IO) {
        questionDao.insertQuestion(questionData)
    }

    suspend fun deleteQuestion(questionData: QuestionData) = withContext(Dispatchers.IO) {
        questionDao.deleteQuestion(questionData)
    }
}