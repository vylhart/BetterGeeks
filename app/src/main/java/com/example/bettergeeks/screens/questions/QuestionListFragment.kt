package com.example.bettergeeks.screens.questions

import android.util.Log
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.bettergeeks.R
import com.example.bettergeeks.screens.recycler.Data
import com.example.bettergeeks.screens.recycler.ListFragment
import com.example.bettergeeks.utils.Common
import com.example.bettergeeks.utils.Common.Companion.KEY_QUESTION_ID
import com.example.bettergeeks.utils.Common.Companion.KEY_TOPIC_ID
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class QuestionListFragment : ListFragment() {
    private val viewModel: QuestionViewModel by viewModels()

    override fun init() {
        Log.i(TAG, "init: ")
       arguments?.getLong(KEY_TOPIC_ID)?.let {
           Log.i(TAG, "init: $it")
           viewModel.getData(it)
       }
        viewModel.list.observe(requireActivity()) {
            adapter.submitList(it)
        }
    }



    companion object {
        private const val TAG = Common.TAG + "TopicFragment"
    }
}