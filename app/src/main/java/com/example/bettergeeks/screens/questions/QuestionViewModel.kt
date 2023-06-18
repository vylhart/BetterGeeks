package com.example.bettergeeks.screens.questions

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bettergeeks.data.dao.remote.FirebaseRepository
import com.example.bettergeeks.data.model.local.QuestionData
import com.example.bettergeeks.data.repository.QuestionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuestionViewModel @Inject constructor(private val repository: QuestionRepository, private val firebaseRepository: FirebaseRepository) :
    ViewModel() {

    private val _list = MutableLiveData(listOf<QuestionData>())
    val list: MutableLiveData<List<QuestionData>> = _list

    fun getData(topicId: String) {
        viewModelScope.launch {
            repository.getQuestionsByTopicId(topicId).collect {
                _list.value = it
            }
        }
        viewModelScope.launch {
            firebaseRepository.getAllQuestionsByTopic(topicId)
        }
    }

    fun updateLikes(questionData: QuestionData, status: LikeStatus) {
        viewModelScope.launch {
            firebaseRepository.updateLikes(questionData, status)
        }
    }

}