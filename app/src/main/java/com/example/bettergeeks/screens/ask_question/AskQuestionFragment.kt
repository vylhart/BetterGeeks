package com.example.bettergeeks.screens.ask_question

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.bettergeeks.data.model.local.TopicData
import com.example.bettergeeks.databinding.FragmentAskQuestionBinding
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class AskQuestionFragment : Fragment() {

    private lateinit var binding: FragmentAskQuestionBinding
    private val viewModel: AskQuestionViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentAskQuestionBinding.inflate(inflater, container, false)
        setObservers()
        return binding.root
    }

    private fun setObservers() {
        binding.buttonGenerateAnswer.setOnClickListener {
            val question = binding.editTextQuestion.text.toString()
            viewModel.generateAnswer(question)
        }

        viewModel.list.observe(viewLifecycleOwner) {
            binding.spinner.adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item , it)
            binding.spinner.onItemSelectedListener = getSpinnerListener()
        }

        viewModel.textResponse.observe(viewLifecycleOwner) {
            when(it) {
                is ResponseData.Success -> {
                    binding.responseLayout.setText(it.data)
                }
                is ResponseData.Error -> {
                    showToast(it.message)
                }
            }
        }

        viewModel.imageResponse.observe(viewLifecycleOwner) {
            when(it) {
                is ResponseData.Success -> {
                    Picasso.get().load(it.data).into(binding.imageView)
                }
                is ResponseData.Error -> {
                    showToast(it.message)
                }
            }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(requireActivity(), message, Toast.LENGTH_SHORT).show()
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