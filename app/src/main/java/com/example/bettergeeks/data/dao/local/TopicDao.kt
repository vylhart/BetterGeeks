package com.example.bettergeeks.data.dao.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.bettergeeks.data.model.local.TopicData
import kotlinx.coroutines.flow.Flow

@Dao
interface TopicDao {
    @Query("SELECT * FROM topic_table ORDER BY likes ASC")
    fun getAllTopics(): Flow<List<TopicData>>

    @Query("SELECT * FROM topic_table WHERE id = :id")
    fun getTopicById(id: Int): Flow<TopicData>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTopic(topicData: TopicData)

    @Delete
    fun deleteTopic(topicData: TopicData)
}