package com.example.bettergeeks.screens.topic

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bettergeeks.data.repository.FirebaseRepository
import com.example.bettergeeks.data.model.local.TopicData
import com.example.bettergeeks.data.repository.TopicRepository
import com.example.bettergeeks.utils.Common.TAG
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TopicViewModel @Inject constructor(private val repository: TopicRepository, private val firebaseRepository: FirebaseRepository) : ViewModel() {

    private val _list = MutableLiveData(listOf<TopicData>())
    val list: LiveData<List<TopicData>> = _list

    init {
        requestData()
    }

    private fun requestData() {
        viewModelScope.launch {
            repository.getAllTopics().collectLatest {
                Log.i(TAG, "requestData: $it")
                _list.value = it
            }
        }

        viewModelScope.launch {
            firebaseRepository.getAllTopics()
        }
    }

    fun addTopic(topicName: String) {
        viewModelScope.launch {
            Log.i(TAG, "addTopic: ")
            firebaseRepository.addTopic(TopicData(topicName, 0))
        }
    }
}