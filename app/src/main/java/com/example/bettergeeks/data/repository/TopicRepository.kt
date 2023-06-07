package com.example.bettergeeks.data.repository

import com.example.bettergeeks.data.dao.local.TopicDao
import com.example.bettergeeks.data.model.local.TopicData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class TopicRepository @Inject constructor(private val topicDao: TopicDao) {

        fun getAllTopics() = topicDao.getAllTopics()

        fun getTopicById(id: Int) = topicDao.getTopicById(id)

        suspend fun insertTopic(topicData: TopicData) = withContext(Dispatchers.IO){
            topicDao.insertTopic(topicData)
        }

        suspend fun deleteTopic(topicData: TopicData) = withContext(Dispatchers.IO) {
            topicDao.deleteTopic(topicData)
        }
}

