package com.example.bettergeeks.screens.topic

import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.bettergeeks.R
import com.example.bettergeeks.data.model.local.TopicData
import com.example.bettergeeks.databinding.CardTopicBinding
import com.example.bettergeeks.screens.recycler.Data
import com.example.bettergeeks.utils.Common
import com.example.bettergeeks.utils.Common.Companion.getColor

class TopicCardViewHolder(private val binding: CardTopicBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(data: TopicData, position: Int) {
        binding.root.setOnClickListener { handleClick(data) }
        binding.textView.text = data.topicName
        binding.imageView.setBackgroundResource(getColor(position))
    }

    private fun handleClick(data: Data) {
        val bundle = bundleOf(Common.KEY_TOPIC_ID to data.id)
        binding.root.findNavController()
            .navigate(R.id.action_topicFragment_to_questionListFragment, bundle)
    }
}

