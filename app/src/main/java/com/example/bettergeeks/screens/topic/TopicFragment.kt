package com.example.bettergeeks.screens.topic

import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.bettergeeks.R
import com.example.bettergeeks.screens.add_topic.AddTopicFragment
import com.example.bettergeeks.screens.recycler.ListFragment
import com.example.bettergeeks.utils.Common
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TopicFragment : ListFragment() {
    private val viewModel: TopicViewModel by viewModels()

    override fun init() {
        Log.i(TAG, "init: ")
        (requireActivity() as MenuHost).addMenuProvider(menuProvider, viewLifecycleOwner, Lifecycle.State.RESUMED)

        binding.recyclerView.layoutManager = GridLayoutManager(activity, 2)
        viewModel.list.observe(requireActivity()) {
            adapter.submitList(it)
            binding.recyclerView.scrollToPosition(0)
        }
    }

    private val menuProvider = object : MenuProvider {
        override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
            menuInflater.inflate(R.menu.nav_menu, menu)
        }

        override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
            when (menuItem.itemId) {
                R.id.nav_ask_question -> {
                    findNavController().navigate(R.id.action_topicFragment_to_askQuestionFragment)
                }

                R.id.nav_add_topic -> {
                    AddTopicFragment().show(parentFragmentManager, "AddTopicFragment")
                }

                else -> {
                    return false
                }
            }
            return true
        }
    }

    companion object {
        private const val TAG = Common.TAG + "TopicFragment"
    }
}