package com.example.bettergeeks.screens.topic

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bettergeeks.R
import com.example.bettergeeks.databinding.FragmentTopicBinding
import com.example.bettergeeks.screens.Adapter
import com.example.bettergeeks.screens.Data
import com.example.bettergeeks.utils.Common

class TopicFragment : Fragment() {
    private lateinit var binding: FragmentTopicBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        Log.i(TAG, "onCreateView: ")
        binding = FragmentTopicBinding.inflate(inflater, container, false)
        setupRecyclerView()
        return binding.root
    }

    private fun setupRecyclerView() {
        binding.recyclerView.layoutManager = LinearLayoutManager(activity)
        binding.recyclerView.adapter = Adapter { handleClick(it) }
    }

    private fun handleClick(data: Data) {
        findNavController().navigate(R.id.action_topicFragment_to_questionListFragment)
    }

    companion object {
        private const val TAG = Common.TAG + "TopicFragment"

        @JvmStatic
        fun newInstance() = TopicFragment()
    }



}