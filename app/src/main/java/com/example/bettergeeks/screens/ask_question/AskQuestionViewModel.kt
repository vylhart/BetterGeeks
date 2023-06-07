package com.example.bettergeeks.screens.ask_question

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bettergeeks.data.dao.remote.OpenAiService
import com.example.bettergeeks.data.model.local.QuestionData
import com.example.bettergeeks.data.model.local.TopicData
import com.example.bettergeeks.data.repository.QuestionRepository
import com.example.bettergeeks.data.repository.TopicRepository
import com.example.bettergeeks.utils.Common
import com.example.bettergeeks.utils.Common.Companion.TAG
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


@HiltViewModel
class AskQuestionViewModel @Inject constructor(
    private val topicRepository: TopicRepository,
    private val questionRepository: QuestionRepository,
    private val openAiService: OpenAiService
    ): ViewModel() {

    var selectedTopic: TopicData? = null
    private val _list = MutableLiveData(listOf<TopicData>())
    val list: LiveData<List<TopicData>> = _list

    private val _response = MutableLiveData("")
    val response: LiveData<String> = _response

    private val _error = MutableLiveData("")
    val error: LiveData<String> = _error

    private var isProcessing = false

    init {
        requestData()
    }

    private fun requestData() {
        Log.i(TAG, "requestData: ")
        viewModelScope.launch {
            topicRepository.getAllTopics().collectLatest {
                Log.i(TAG, "requestData: $it")
                _list.value = it
            }
        }
    }

    private fun insertData(question: String, answer: String, topicData: TopicData) {
        viewModelScope.launch {
            questionRepository.insertQuestion(QuestionData(question, answer, topicData.id))
        }
    }

    fun generateAnswer(question: String) {
        if (question.isBlank() || isProcessing || selectedTopic == null) return
        viewModelScope.launch {
            isProcessing = true
            val request = Common.getChatGptRequest(question)

            val response = withContext(Dispatchers.IO) {
                openAiService.generateCompletion(request)
            }
            if (response.isSuccessful) {
                val chatGptResponse = response.body()
                if (chatGptResponse != null && chatGptResponse.choices.isNotEmpty()) {
                    val choice = chatGptResponse.choices[0]
                    val answer = choice.message.content
                    insertData(question, answer, selectedTopic!!)
                    _response.value = answer
                } else {
                    _error.value = "Empty response or missing choice."
                }
            } else {
                _error.value = "API request failed: ${response.code()}"
            }
            isProcessing = false
        }
    }
}