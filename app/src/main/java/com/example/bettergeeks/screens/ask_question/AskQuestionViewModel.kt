package com.example.bettergeeks.screens.ask_question

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bettergeeks.data.repository.FirebaseRepository
import com.example.bettergeeks.data.repository.OpenAiRepository
import com.example.bettergeeks.data.model.local.QuestionData
import com.example.bettergeeks.data.model.local.TopicData
import com.example.bettergeeks.data.repository.TopicRepository
import com.example.bettergeeks.utils.Common.TAG
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.Locale
import javax.inject.Inject


@HiltViewModel
class AskQuestionViewModel @Inject constructor(
    private val topicRepository: TopicRepository,
    private val firebaseRepository: FirebaseRepository,
    private val openAiRepository: OpenAiRepository
) : ViewModel() {


    var selectedTopic: TopicData? = null

    private val _list = MutableLiveData(listOf<TopicData>())
    val list: LiveData<List<TopicData>> = _list

    private val _textResponse = MutableLiveData<ResponseData<String>>()
    val textResponse: LiveData<ResponseData<String>> = _textResponse

    private val _imageResponse = MutableLiveData<ResponseData<String>>()
    val imageResponse: LiveData<ResponseData<String>> = _imageResponse

    private var isProcessing = false

    init {
        requestData()
    }

    private fun requestData() {
        viewModelScope.launch {
            topicRepository.getAllTopics().collectLatest {
                _list.value = it
            }
        }
    }

    fun generateAnswer(question: String) {
        Log.i(TAG, "generateAnswer: $question")
        if (question.isBlank() || isProcessing || selectedTopic == null) return
        val formattedQuestion = question.trim().replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString() }
        val additionalQuestion = ", Answer this question in context of ${selectedTopic?.topicName}"

        viewModelScope.launch {
            isProcessing = true
            val answer = openAiRepository.generateAnswer(formattedQuestion + additionalQuestion)
            _textResponse.value = answer
            if (answer is ResponseData.Success) {
                generateImageFromText(formattedQuestion, answer.data)
            }
            else {
                isProcessing = false
            }
        }
    }

    private fun generateImageFromText(question: String, text: String) {
        if (text.isBlank() || selectedTopic == null) return
        Log.i(TAG, "generateImageFromText: $text")

        viewModelScope.launch {
            val response = openAiRepository.generateImageFromText(text)
            _imageResponse.value = response
            isProcessing = false
            if (response is ResponseData.Success) {
                firebaseRepository.insertQuestion(QuestionData(question, text, selectedTopic!!.id, response.data))
            }
        }
    }
}

sealed class ResponseData<out T> {
    data class Success<out T>(val data: T) : ResponseData<T>()
    data class Error(val message: String) : ResponseData<Nothing>()
}