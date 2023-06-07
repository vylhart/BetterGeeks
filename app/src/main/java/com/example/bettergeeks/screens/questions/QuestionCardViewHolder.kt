package com.example.bettergeeks.screens.questions

import androidx.recyclerview.widget.RecyclerView
import com.example.bettergeeks.data.model.local.QuestionData
import com.example.bettergeeks.databinding.CardQuestionBinding
import com.example.bettergeeks.screens.recycler.Data

class QuestionCardViewHolder(private val binding: CardQuestionBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(data: QuestionData, callback: (Data) -> Unit) {
        binding.root.setOnClickListener { callback(data) }
        binding.textView.text = data.question
    }
}
