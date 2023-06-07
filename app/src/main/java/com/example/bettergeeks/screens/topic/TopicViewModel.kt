package com.example.bettergeeks.screens.topic

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bettergeeks.data.model.local.TopicData
import com.example.bettergeeks.data.repository.TopicRepository
import com.example.bettergeeks.utils.Common.Companion.TAG
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TopicViewModel @Inject constructor(private val repo: TopicRepository): ViewModel() {

    private val _list = MutableLiveData(listOf<TopicData>())
    val list : LiveData<List<TopicData>> = _list

    init {
        requestData()
        //insertData()
    }

    private fun requestData() {
        viewModelScope.launch {
            repo.getAllTopics().collectLatest {
                Log.i(TAG, "requestData: $it")
                _list.value = it
            }
        }
    }

    private fun insertData() {
        val topicDataList = listOf(
            TopicData(
                topicName = "Technology",
                likes = 10
            ),
            TopicData(
                topicName = "Sports",
                likes = 5
            ),
            TopicData(
                topicName = "Movies",
                likes = 2
            ),
        )

        viewModelScope.launch {
            repo.insertTopic(topicDataList[0])
            repo.insertTopic(topicDataList[1])
            repo.insertTopic(topicDataList[2])
        }
    }
}