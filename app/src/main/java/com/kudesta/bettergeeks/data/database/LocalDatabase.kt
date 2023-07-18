package com.kudesta.bettergeeks.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.kudesta.bettergeeks.data.dao.local.QuestionDao
import com.kudesta.bettergeeks.data.dao.local.TopicDao
import com.kudesta.bettergeeks.data.model.local.QuestionData
import com.kudesta.bettergeeks.data.model.local.TopicData

@Database(entities = [TopicData::class, QuestionData::class], version = 1, exportSchema = false)
abstract class LocalDatabase : RoomDatabase() {
    abstract fun topicDao(): TopicDao
    abstract fun questionDao(): QuestionDao
}

