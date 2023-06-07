package com.example.bettergeeks.screens.topic

import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.core.os.bundleOf
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
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
        (requireActivity() as MenuHost).addMenuProvider(menuProvider, viewLifecycleOwner, Lifecycle.State.RESUMED)
        binding.recyclerView.layoutManager = GridLayoutManager(activity, 2)
        viewModel.list.observe(requireActivity()) {
            adapter.submitList(it)
        }
    }

    private val menuProvider = object : MenuProvider {
        override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
            menuInflater.inflate(R.menu.nav_menu, menu)
        }

        override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
            when(menuItem.itemId) {
                R.id.nav_ask_question -> {
                    findNavController().navigate(R.id.action_topicFragment_to_askQuestionFragment)
                }
                R.id.nav_add_topic -> {
                    findNavController().navigate(R.id.action_topicFragment_to_addTopicFragment)
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