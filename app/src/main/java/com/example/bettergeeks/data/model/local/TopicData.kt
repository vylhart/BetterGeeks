package com.example.bettergeeks.data.model.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.bettergeeks.screens.recycler.Data
import com.example.bettergeeks.screens.recycler.ViewTypes

@Entity(tableName = "topic_table")
data class TopicData(
    val topicName: String,
    val likes: Int,
    @PrimaryKey(autoGenerate = true)
    override val id: Long = 0,
) : Data {

    override fun getViewType(): Int {
        return ViewTypes.TOPIC.ordinal
    }
}
