package com.example.bettergeeks.screens.topic

import android.util.Log
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.bettergeeks.R
import com.example.bettergeeks.screens.recycler.Data
import com.example.bettergeeks.screens.recycler.ListFragment
import com.example.bettergeeks.screens.recycler.ViewTypes
import com.example.bettergeeks.utils.Common
import com.example.bettergeeks.utils.Common.Companion.KEY_TOPIC_ID
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TopicFragment : ListFragment() {
    private val viewModel:TopicViewModel by viewModels()

    override fun init() {
        Log.i(TAG, "init: ")
        binding.recyclerView.layoutManager = GridLayoutManager(activity, 2)

        viewModel.list.observe(requireActivity()) {
            adapter.submitList(it)
        }
    }

    override fun getViewType() = ViewTypes.TOPIC

    override fun handleClick(data: Data) {
        val bundle = bundleOf(KEY_TOPIC_ID to data.id)
        findNavController().navigate(R.id.action_topicFragment_to_questionListFragment, bundle)
    }

    companion object {
        private const val TAG = Common.TAG + "TopicFragment"
    }


}