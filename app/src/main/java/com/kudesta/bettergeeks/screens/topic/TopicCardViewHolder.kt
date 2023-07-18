package com.kudesta.bettergeeks.screens.topic

import android.util.Log
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.kudesta.bettergeeks.R
import com.kudesta.bettergeeks.data.model.local.TopicData
import com.kudesta.bettergeeks.databinding.CardTopicBinding
import com.kudesta.bettergeeks.screens.recycler.Data
import com.kudesta.bettergeeks.utils.Common
import com.kudesta.bettergeeks.utils.Common.TAG
import com.kudesta.bettergeeks.utils.Common.getColor

class TopicCardViewHolder(private val binding: CardTopicBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(data: TopicData, position: Int) {
        binding.apply {
            root.setOnClickListener { handleClick(data) }
            textView.text = data.topicName
            cardView.setBackgroundResource(getColor(position))
        }
    }

    private fun handleClick(data: Data) {
        val bundle = bundleOf(Common.KEY_TOPIC_ID to data.id)
        Log.i(TAG, "handleClick: data.id ${data.id}")
        binding.root.findNavController().navigate(R.id.action_topicFragment_to_questionListFragment, bundle)
    }
}

