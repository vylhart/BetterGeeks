package com.example.bettergeeks.screens.questions

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bettergeeks.data.model.local.QuestionData
import com.example.bettergeeks.data.repository.QuestionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuestionViewModel @Inject constructor(private val repository: QuestionRepository) : ViewModel() {

    private val _list = MutableLiveData(listOf<QuestionData>())
    val list : MutableLiveData<List<QuestionData>> = _list

    init {
        insertData()
        //getData()
    }

    private fun insertData() {
        val questionDataList = listOf(
            QuestionData(
                question = "What is the capital of France?",
                answer = "Paris"
            ),
            QuestionData(
                question = "Who painted the Mona Lisa?",
                answer = "Leonardo da Vinci"
            ),
            QuestionData(
                question = "What is the tallest mountain in the world?",
                answer = "Mount Everest"
            ),
            // Add more QuestionData objects as needed
        )

        viewModelScope.launch {
            repository.insertQuestion(questionDataList[0])
            repository.insertQuestion(questionDataList[1])
            repository.insertQuestion(questionDataList[2])
        }
    }

    fun getData(topicId: Long) {
        viewModelScope.launch {
            repository.getQuestionsByTopicId(topicId).collect {
                _list.value = it
            }
        }
    }

}