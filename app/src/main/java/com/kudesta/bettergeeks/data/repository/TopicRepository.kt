package com.kudesta.bettergeeks.data.repository

import com.kudesta.bettergeeks.data.dao.local.TopicDao
import com.kudesta.bettergeeks.data.model.local.TopicData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class TopicRepository @Inject constructor(private val topicDao: TopicDao) {

    fun getAllTopics() = flow {
        topicDao.getAllTopics().collect {
            emit(it.sortedBy { topicData -> topicData.topicName })
        }
    }

    fun getTopicById(id: Int) = topicDao.getTopicById(id)

    suspend fun insertTopic(topicData: TopicData) = withContext(Dispatchers.IO) {
        topicDao.insertTopic(topicData)
    }

    suspend fun deleteTopic(topicData: TopicData) = withContext(Dispatchers.IO) {
        topicDao.deleteTopic(topicData)
    }
}

