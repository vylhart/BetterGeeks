package com.kudesta.bettergeeks.data.dao.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.kudesta.bettergeeks.data.model.local.QuestionData
import kotlinx.coroutines.flow.Flow

@Dao
interface QuestionDao {
    @Query("SELECT * FROM question_table ORDER BY id ASC")
    fun getAllQuestions(): Flow<List<QuestionData>>

    @Query("SELECT * FROM question_table WHERE topicId = :topicId")
    fun getQuestionsByTopicId(topicId: String): Flow<List<QuestionData>>

    @Query("SELECT * FROM question_table WHERE id = :id")
    fun getQuestionById(id: Int): Flow<QuestionData>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertQuestion(question: QuestionData)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertQuestionList(list: List<QuestionData>)

    @Delete
    fun deleteQuestion(question: QuestionData)
}

