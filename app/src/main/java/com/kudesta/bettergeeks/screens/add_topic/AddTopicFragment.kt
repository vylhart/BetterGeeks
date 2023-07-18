package com.kudesta.bettergeeks.screens.add_topic

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.kudesta.bettergeeks.databinding.FragmentAddTopicBinding
import com.kudesta.bettergeeks.screens.topic.TopicViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class AddTopicFragment : DialogFragment() {
    private lateinit var binding: FragmentAddTopicBinding
    private val viewModel: TopicViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddTopicBinding.inflate(inflater, container, false)
        dialog?.setTitle("Add Topic")
        binding.addTopicButton.setOnClickListener { addTopic() }
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        setDialogSize()
    }

    // Set dialog size to match parent
    private fun setDialogSize() {
        val params: ViewGroup.LayoutParams? = dialog?.window?.attributes
        params?.width = WindowManager.LayoutParams.MATCH_PARENT
        params?.height = WindowManager.LayoutParams.WRAP_CONTENT
        dialog?.window?.attributes = params as WindowManager.LayoutParams
    }

    private fun addTopic() {
        val topic = binding.addTopicEditText.text.toString()
        viewModel.addTopic(topic)
        dismiss()
    }
}