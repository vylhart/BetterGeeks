package com.example.bettergeeks.screens.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.bettergeeks.data.model.local.QuestionData
import com.example.bettergeeks.data.model.local.TopicData
import com.example.bettergeeks.databinding.CardQuestionBinding
import com.example.bettergeeks.databinding.CardTopicBinding
import com.example.bettergeeks.screens.questions.QuestionCardViewHolder
import com.example.bettergeeks.screens.topic.TopicCardViewHolder

class Adapter(val types: ViewTypes, private val callback: (Data) -> Unit) :
    ListAdapter<Data, RecyclerView.ViewHolder>(callbackHandler) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ViewTypes.TOPIC.ordinal -> {
                val binding =
                    CardTopicBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                TopicCardViewHolder(binding)
            }

            else -> {
                val binding =
                    CardQuestionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                QuestionCardViewHolder(binding)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is TopicCardViewHolder -> holder.bind(getItem(position) as TopicData, position, callback)
            is QuestionCardViewHolder -> holder.bind(getItem(position) as QuestionData, callback)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return types.ordinal
    }
}

interface Data {
    val id: Long
    fun getViewType(): Int
}



private val callbackHandler = object : DiffUtil.ItemCallback<Data>() {
    override fun areItemsTheSame(oldItem: Data, newItem: Data): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Data, newItem: Data): Boolean {
        return oldItem.id == newItem.id
    }
}

enum class ViewTypes(value: Int) {
    TOPIC(0),
    QUESTION(1)
}