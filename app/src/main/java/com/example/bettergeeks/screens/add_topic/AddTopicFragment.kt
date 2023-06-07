package com.example.bettergeeks.screens.add_topic

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.bettergeeks.databinding.FragmentAddTopicBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class AddTopicFragment : Fragment() {
    private lateinit var binding: FragmentAddTopicBinding
    private val viewModel: AddTopicViewModel by viewModels()



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentAddTopicBinding.inflate(inflater, container, false)
        binding.addTopicButton.setOnClickListener { addTopic() }
        return binding.root
    }

    private fun addTopic() {
        val topic = binding.addTopicEditText.text.toString()
        viewModel.addTopic(topic)
    }
}