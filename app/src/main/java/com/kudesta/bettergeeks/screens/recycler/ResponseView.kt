package com.kudesta.bettergeeks.screens.recycler

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.TextView
import com.kudesta.bettergeeks.databinding.ResponseViewBinding


class ResponseView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) : LinearLayout(context, attrs) {

    private val binding = ResponseViewBinding.inflate(LayoutInflater.from(context), this, true)

    fun setQuestion(question: String) {
        //binding.questionText.text = question
    }
    fun setText(text: String) {
        //binding.buttonSaveAnswer.visibility = VISIBLE
        val textView = TextView(context)
        textView.text = text
        binding.responseLayout.addView(textView)
//        val pieces = text.split("\n\n")
//        for ((i, piece) in pieces.withIndex()) {
//            if (i % 2 == 1) {
//                val codeView = CodeTextView(context)
//                codeView.setText(piece)
//                binding.responseLayout.addView(codeView)
//            } else {
//                val textView = TextView(context)
//                textView.text = piece
//                binding.responseLayout.addView(textView)
//            }
//            val textView = TextView(context)
//            textView.text = piece
//            binding.responseLayout.addView(textView)
//        }
    }

//    fun setClickListener(listener: () -> Unit) {
//        binding.buttonSaveAnswer.setOnClickListener{ listener() }
//    }

}