package com.example.bettergeeks.screens.recycler

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.example.bettergeeks.databinding.ResponseViewBinding
import com.example.bettergeeks.screens.ask_question.CodeTextView


class ResponseView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null,
) : LinearLayout(context, attrs) {

    private val binding = ResponseViewBinding.inflate(LayoutInflater.from(context), this, true)

    fun setText(text: String) {
        val pieces = text.split("\n\n")
        for ((i, piece) in pieces.withIndex()) {
            if (i % 2 == 1) {
                val codeView = CodeTextView(context)
                codeView.setText(piece)
                binding.responseLayout.addView(codeView)
            } else {
                val textView = android.widget.TextView(context)
                textView.text = piece
                binding.responseLayout.addView(textView)
            }
        }
    }

}