package com.example.bettergeeks.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.bettergeeks.data.dao.local.QuestionDao
import com.example.bettergeeks.data.dao.local.TopicDao
import com.example.bettergeeks.data.model.local.QuestionData
import com.example.bettergeeks.data.model.local.TopicData

@Database(entities = [TopicData::class, QuestionData::class], version = 1, exportSchema = false)
abstract class LocalDatabase : RoomDatabase() {
    abstract fun topicDao(): TopicDao
    abstract fun questionDao(): QuestionDao
}

