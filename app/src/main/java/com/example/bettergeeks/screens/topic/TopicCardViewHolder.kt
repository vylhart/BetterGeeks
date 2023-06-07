package com.example.bettergeeks.screens.topic

import androidx.recyclerview.widget.RecyclerView
import com.example.bettergeeks.data.model.local.TopicData
import com.example.bettergeeks.databinding.CardTopicBinding
import com.example.bettergeeks.screens.recycler.Data
import com.example.bettergeeks.utils.Common.Companion.getColor

class TopicCardViewHolder(private val binding: CardTopicBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(data: TopicData, position: Int, callback: (Data) -> Unit) {
        binding.root.setOnClickListener { callback(data) }
        binding.textView.text = data.topicName
        binding.imageView.setBackgroundResource(getColor(position))
    }
}

