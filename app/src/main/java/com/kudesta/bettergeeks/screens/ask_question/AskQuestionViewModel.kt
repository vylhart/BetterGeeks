package com.kudesta.bettergeeks.screens.ask_question

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kudesta.bettergeeks.data.model.local.QuestionData
import com.kudesta.bettergeeks.data.model.local.TopicData
import com.kudesta.bettergeeks.data.repository.FirebaseRepository
import com.kudesta.bettergeeks.data.repository.OpenAiRepository
import com.kudesta.bettergeeks.data.repository.TopicRepository
import com.kudesta.bettergeeks.utils.Common
import com.kudesta.bettergeeks.utils.Common.TAG
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
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

    private val _textResponse = MutableLiveData<ResponseData<QuestionData>>()
    val textResponse: LiveData<ResponseData<QuestionData>> = _textResponse

    private val _parseResponse = MutableLiveData<List<QuestionData>>()
    val parseResponse: LiveData<List<QuestionData>> = _parseResponse

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
            isProcessing = false
            when(answer) {
                is ResponseData.Success -> {
                    _textResponse.value = ResponseData.Success(QuestionData(formattedQuestion, answer.data, selectedTopic!!.id, ""))
                }
                is ResponseData.Error -> {
                    _textResponse.value = answer
                }
            }
        }
    }

    fun parseAnswer() {
        if(_textResponse.value !is ResponseData.Success) return

        val originalQuestion = (_textResponse.value as ResponseData.Success).data.answer ?: return
        val list = Common.parseQuestionList(originalQuestion)
        val deferred =  list.map {question ->
            val formattedQuestion = question.trim().replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString() }
            val additionalQuestion = ", Answer this question in context of ${selectedTopic?.topicName}"
            viewModelScope.async {
                val answer = openAiRepository.generateAnswer(formattedQuestion + additionalQuestion)
                if (answer is ResponseData.Success) {
                    return@async QuestionData(formattedQuestion, answer.data, selectedTopic!!.id, "")
                }
                return@async null
            }
        }

        viewModelScope.launch {
            _parseResponse.value = deferred.awaitAll().filterNotNull()
        }

    }

    fun save() {
        Log.i(TAG, "save: ")
        viewModelScope.launch {
            textResponse.value?.let {
                if (it is ResponseData.Success) {
                    Log.i(TAG, "save: ${it.data}")
                    firebaseRepository.insertQuestion(it.data)
                }
            }
        }
    }
}

sealed class ResponseData<out T> {
    data class Success<out T>(val data: T) : ResponseData<T>()
    data class Error(val message: String) : ResponseData<Nothing>()
}