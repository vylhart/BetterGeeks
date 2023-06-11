package com.example.bettergeeks.screens.questions

import android.util.Log
import android.view.View
import android.view.animation.LinearInterpolator
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DefaultItemAnimator
import com.example.bettergeeks.screens.recycler.ListFragment
import com.example.bettergeeks.utils.Common
import com.example.bettergeeks.utils.Common.Companion.KEY_TOPIC_ID
import com.yuyakaido.android.cardstackview.CardStackLayoutManager
import com.yuyakaido.android.cardstackview.CardStackListener
import com.yuyakaido.android.cardstackview.Direction
import com.yuyakaido.android.cardstackview.SwipeableMethod
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class QuestionListFragment : ListFragment(), CardStackListener {
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

        binding.recyclerView.isVisible = false
        binding.cardStackView.isVisible = true

        binding.cardStackView.layoutManager = CardStackLayoutManager(requireContext(), this).apply {
            setSwipeableMethod(SwipeableMethod.AutomaticAndManual)
            setOverlayInterpolator(LinearInterpolator())
        }

        binding.cardStackView.adapter = adapter
        binding.cardStackView.itemAnimator.apply {
            if (this is DefaultItemAnimator) {
                supportsChangeAnimations = false
            }
        }

    }

    override fun onCardDragging(direction: Direction?, ratio: Float) {
        //TODO("Not yet implemented")
    }

    override fun onCardSwiped(direction: Direction?) {
        if (direction == Direction.Right) {

        } else if (direction == Direction.Left) {

        }
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

    companion object {
        private const val TAG = Common.TAG + "TopicFragment"
    }
}