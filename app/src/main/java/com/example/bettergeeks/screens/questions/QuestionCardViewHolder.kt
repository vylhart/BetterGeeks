package com.example.bettergeeks.screens.questions

import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.bettergeeks.R
import com.example.bettergeeks.data.model.local.QuestionData
import com.example.bettergeeks.databinding.CardQuestionBinding
import com.example.bettergeeks.screens.recycler.Data
import com.example.bettergeeks.utils.Common
import com.squareup.picasso.Picasso

class QuestionCardViewHolder(private val binding: CardQuestionBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(data: QuestionData) {
        binding.root.setOnClickListener { handleClick(data) }
        binding.textView.text = data.question
        binding.answerView.text = data.answer
        Picasso.get().load(data.imageUrl).into(binding.imageView)
    }
    private fun handleClick(data: Data) {
        val bundle = bundleOf(Common.KEY_QUESTION_ID to data)
        binding.root.findNavController().navigate(R.id.action_questionListFragment_to_detailFragment, bundle)
    }
}
