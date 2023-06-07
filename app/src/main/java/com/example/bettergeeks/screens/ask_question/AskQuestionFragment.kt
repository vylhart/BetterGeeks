package com.example.bettergeeks.screens.ask_question

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.bettergeeks.data.model.local.TopicData
import com.example.bettergeeks.databinding.FragmentAskQuestionBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class AskQuestionFragment : Fragment() {
    private lateinit var binding: FragmentAskQuestionBinding
    private val viewModel: AskQuestionViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?, ): View {
        binding = FragmentAskQuestionBinding.inflate(inflater, container, false)
        setBindings()
        return binding.root
    }

    private fun setBindings() {
        binding.buttonGenerateAnswer.setOnClickListener {
            val question = binding.editTextQuestion.text.toString()
            viewModel.generateAnswer(question)
        }
        viewModel.list.observe(viewLifecycleOwner) {
            binding.spinner.adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item , it)
            binding.spinner.onItemSelectedListener = getSpinnerListener()
        }
        viewModel.response.observe(viewLifecycleOwner) {
            binding.buttonGenerateAnswer.visibility = View.VISIBLE
            createResponseView(it)
        }
        viewModel.error.observe(viewLifecycleOwner) {
            binding.buttonGenerateAnswer.visibility = View.VISIBLE
            showToast(it)
        }
    }


    private fun showToast(message: String) {
        Toast.makeText(requireActivity(), message, Toast.LENGTH_SHORT).show()
    }

    private fun createResponseView(text: String) {
        binding.responseLayout.removeAllViews()
        val pieces = text.split("\n\n")
        for ((i, piece) in pieces.withIndex()) {
            if (i % 2 == 1) {
                val codeView = CodeTextView(requireContext())
                codeView.setText(piece)
                binding.responseLayout.addView(codeView)
            } else {
                val textView = TextView(requireContext())
                textView.text = piece
                binding.responseLayout.addView(textView)
            }
        }
    }

    private fun getSpinnerListener(): AdapterView.OnItemSelectedListener {
        return object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val topicData = parent?.getItemAtPosition(position) as TopicData
                viewModel.selectedTopic = topicData
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                viewModel.selectedTopic = null
            }
        }
    }
}


