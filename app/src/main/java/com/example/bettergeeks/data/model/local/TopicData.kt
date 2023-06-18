package com.example.bettergeeks.data.model.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.bettergeeks.screens.recycler.Data
import com.example.bettergeeks.screens.recycler.ViewTypes
import java.util.UUID

@Entity(tableName = "topic_table")
data class TopicData(
    val topicName: String = "",
    val likes: Int = 0,
    @PrimaryKey
    override val id: String = UUID.randomUUID().toString(),
) : Data {

    override fun getViewType(): Int {
        return ViewTypes.TOPIC.ordinal
    }
}
