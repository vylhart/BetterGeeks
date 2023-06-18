package com.example.bettergeeks.data.dao.remote

import android.util.Log
import com.example.bettergeeks.data.dao.local.QuestionDao
import com.example.bettergeeks.data.model.local.QuestionData
import com.example.bettergeeks.data.model.local.TopicData
import com.example.bettergeeks.data.repository.TopicRepository
import com.example.bettergeeks.screens.questions.LikeStatus
import com.example.bettergeeks.utils.Common.TAG
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.util.UUID
import javax.inject.Inject


class FirebaseRepository @Inject constructor(private val topicRepository: TopicRepository, private val questionDao: QuestionDao) {
    private val firebase: FirebaseFirestore = Firebase.firestore
    private val scope = CoroutineScope(Dispatchers.IO)
    val questionPath = "questions"
    val topicPath = "topics"
    val userId = UUID.randomUUID().toString()


    fun getAllTopics() {
        firebase.collection(topicPath).addSnapshotListener { querySnapshot, exception ->
            if (exception != null || querySnapshot == null) {
                Log.i(TAG, "getAllTopics: $exception")
                return@addSnapshotListener
            }

            scope.launch {
                for (document in querySnapshot) {
                    val topic = document.toObject(TopicData::class.java)
                    topicRepository.insertTopic(topic)
                }
            }
        }
    }


    suspend fun addTopic(topic: TopicData) {
        withContext(Dispatchers.IO) {
            firebase.collection(topicPath).add(topic).await()
        }
    }


    fun insertQuestion(data: QuestionData)  {
        scope.launch {
            firebase.collection(questionPath).document(data.topicId).collection(questionPath).document(data.id).set(data).await()
        }
    }

    fun getAllQuestionsByTopic(topicId: String) {
        firebase.collection(questionPath).document(topicId).collection(questionPath).addSnapshotListener { querySnapshot, exception ->
            if (exception != null || querySnapshot == null) {
                Log.i(TAG, "getAllQuestionsByTopic: $exception")
                return@addSnapshotListener
            }

            for (doc in querySnapshot) {
                scope.launch {
                    val data = doc.toObject(QuestionData::class.java)
                    val topicRef = firebase.collection(questionPath).document(data.topicId).collection(questionPath).document(data.id)
                    val likes = topicRef.collection("likes").count().query.get().await().count()
                    val dislikes = topicRef.collection("dislikes").count().query.get().await().count()
                    val isLikedByMe = topicRef.collection("likes").document(userId).get().await().exists()
                    val isDislikedByMe = topicRef.collection("dislikes").document(userId).get().await().exists()
                    Log.i(TAG, "getAllQuestionsByTopic: $likes $dislikes $isLikedByMe $isDislikedByMe $userId")
                    questionDao.insertQuestion(data.copy(likes = likes, dislikes = dislikes, isLiked = isLikedByMe, isDisliked = isDislikedByMe))
                }
            }
        }
    }



    fun updateLikes(data: QuestionData, status: LikeStatus) {
        Log.i(TAG, "updateLikes: ")

        scope.launch {
            val topicRef = firebase.collection(questionPath).document(data.topicId).collection(questionPath).document(data.id)
            when(status) {
                LikeStatus.LIKED -> {
                    topicRef.collection("likes").document(userId).set(hashMapOf("userId" to userId)).await()
                    topicRef.collection("dislikes").document(userId).delete().await()
                }
                LikeStatus.DISLIKED -> {
                    topicRef.collection("dislikes").document(userId).set(hashMapOf("userId" to userId)).await()
                    topicRef.collection("likes").document(userId).delete().await()
                }
                LikeStatus.NEUTRAL -> {
                    topicRef.collection("dislikes").document(userId).delete().await()
                    topicRef.collection("likes").document(userId).delete().await()
                }
            }

        // update like count in firebase atomically using transaction
//            firebase.runTransaction { transaction ->
//                transaction.get(topicRef).toObject(QuestionData::class.java)?.let {
//                    transaction.update(topicRef, "likes", it.likes + likes)
//                }
//            }.await()
//
//            firebase.runTransaction { transaction ->
//                transaction.get(topicRef).toObject(QuestionData::class.java)?.let {
//                    transaction.update(topicRef, "dislikes", it.dislikes + dislikes)
//                }
//            }.await()
        }
    }




}

