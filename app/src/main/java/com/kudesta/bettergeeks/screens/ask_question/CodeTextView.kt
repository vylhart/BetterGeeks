package com.kudesta.bettergeeks.screens.ask_question

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.kudesta.bettergeeks.databinding.CodeTextViewBinding

class CodeTextView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null,
) : FrameLayout(context, attrs) {

    private val binding = CodeTextViewBinding.inflate(LayoutInflater.from(context), this, true)

    fun setText(text: String) {
        binding.textView.text = text.replace("```", "").trim()
    }


}