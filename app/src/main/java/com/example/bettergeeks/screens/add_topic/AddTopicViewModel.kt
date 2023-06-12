package com.example.bettergeeks.screens.add_topic

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bettergeeks.data.model.local.TopicData
import com.example.bettergeeks.data.repository.TopicRepository
import com.example.bettergeeks.utils.Common.TAG
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddTopicViewModel @Inject constructor(private val repository: TopicRepository) : ViewModel() {

    fun addTopic(topicName: String) {
        viewModelScope.launch {
            Log.i(TAG, "addTopic: ")
            repository.insertTopic(TopicData(topicName, 0))
        }
    }
}