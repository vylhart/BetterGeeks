package com.kudesta.bettergeeks.screens.questions

import android.util.Log
import android.view.View
import android.view.animation.LinearInterpolator
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DefaultItemAnimator
import com.kudesta.bettergeeks.data.shared_preference.LocalCache
import com.kudesta.bettergeeks.screens.recycler.ListFragment
import com.kudesta.bettergeeks.utils.Common
import com.kudesta.bettergeeks.utils.Common.KEY_TOPIC_ID
import com.yuyakaido.android.cardstackview.CardStackLayoutManager
import com.yuyakaido.android.cardstackview.CardStackListener
import com.yuyakaido.android.cardstackview.Direction
import com.yuyakaido.android.cardstackview.SwipeableMethod
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class QuestionListFragment : ListFragment(), CardStackListener {
    private val viewModel: QuestionViewModel by viewModels()
    private var manager: CardStackLayoutManager? = null

    override fun init() {
        Log.i(TAG, "init: ")
        arguments?.getString(KEY_TOPIC_ID)?.let {
            Log.i(TAG, "init: $it")
            viewModel.topicId = it
            viewModel.getData(it)
            val isProcessed = LocalCache.isRequestProcessedWithinTimeWindow(requireActivity(), it)
            if (!isProcessed) {
                viewModel.fetchData(it)
                LocalCache.markRequestAsProcessed(requireActivity(), it)
            }
        }

         manager = CardStackLayoutManager(requireContext(), this).apply {
            setSwipeableMethod(SwipeableMethod.AutomaticAndManual)
            setOverlayInterpolator(LinearInterpolator())
        }
        binding.cardStackView.layoutManager = manager

        viewModel.currentPosition = LocalCache.getLastCardPosition(requireContext(), viewModel.topicId)
        Log.i(TAG, "init: ${viewModel.currentPosition}")

        binding.recyclerView.isVisible = false
        binding.cardStackView.isVisible = true

        binding.cardStackView.adapter = adapter
        binding.cardStackView.itemAnimator.apply {
            if (this is DefaultItemAnimator) {
                supportsChangeAnimations = false
            }
        }

        viewModel.list.observe(requireActivity()) {
            adapter.submitList(it)

            binding.cardStackView.post{
                binding.cardStackView.smoothScrollToPosition(viewModel.currentPosition)
            }
        }
    }

    override fun onCardDragging(direction: Direction?, ratio: Float) {
        //TODO("Not yet implemented")
    }

    override fun onCardSwiped(direction: Direction?) {
        // Check if all cards are finished and rewind the stack.
        manager?.topPosition?.let { viewModel.currentPosition = it }
        if (viewModel.currentPosition == adapter.currentList.size - 1) {
            viewModel.currentPosition = 0
            rewindCardStack()
        }
        Log.i(TAG, "onCardSwiped: ${viewModel.currentPosition}")

    }

    override fun onCardRewound() {
        //TODO("Not yet implemented")
    }

    override fun onCardCanceled() {
        //TODO("Not yet implemented")
    }

    override fun onCardAppeared(view: View?, position: Int) {
        //TODO("Not yet implemented")
    }

    override fun onCardDisappeared(view: View?, position: Int) {
        //TODO("Not yet implemented")
    }

    private fun rewindCardStack() {
        // Reset the CardStackView to the initial position.
        binding.cardStackView.rewind()
    }

    override fun onPause() {
        super.onPause()
        LocalCache.saveLastCardPosition(requireContext(), viewModel.topicId, viewModel.currentPosition)
    }

    companion object {
        private const val TAG = Common.TAG + "TopicFragment"
    }
}