package com.kudesta.bettergeeks.screens.questions

import android.util.Log
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.kudesta.bettergeeks.MainActivity
import com.kudesta.bettergeeks.R
import com.kudesta.bettergeeks.data.model.local.QuestionData
import com.kudesta.bettergeeks.databinding.CardQuestionBinding
import com.kudesta.bettergeeks.screens.recycler.Data
import com.kudesta.bettergeeks.utils.Common
import com.kudesta.bettergeeks.utils.Common.TAG

class QuestionCardViewHolder(private val binding: CardQuestionBinding, activity: FragmentActivity?) :
    RecyclerView.ViewHolder(binding.root) {

    private val selectedColor = ContextCompat.getColorStateList(binding.root.context, R.color.blue)
    private val neutralColor = ContextCompat.getColorStateList(binding.root.context, R.color.white)
    private val randomColor = ContextCompat.getDrawable(binding.root.context, Common.randomColor())
    private var state = LikeStatus.NEUTRAL
    private var likes = 0
    private var dislikes = 0

    private val viewModel: QuestionViewModel by lazy {
        ViewModelProvider(activity as MainActivity)[QuestionViewModel::class.java]
    }

    fun bind(data: QuestionData) {
        binding.apply {
            answerView.setOnClickListener { handleClick(data) }
            imageViewText.text = data.question
            answerView.text = data.answer
            imageViewText.background = randomColor

            likes = data.likes
            dislikes = data.dislikes
            updateLikeAndDislikeCounts()

            if (data.isLiked) {
                state = LikeStatus.LIKED
                likeIv.imageTintList = selectedColor
            } else if (data.isDisliked) {
                state = LikeStatus.DISLIKED
                dislikeIv.imageTintList = selectedColor
            }
        }
        likesListener(data)
    }

    private fun likesListener(data: QuestionData) {
        binding.apply {
            likeIv.setOnClickListener {
                Log.i(TAG, "bind: like clicked")
                when (state) {
                    LikeStatus.NEUTRAL -> {
                        updateLikeStatus(LikeStatus.LIKED, data)
                    }

                    LikeStatus.LIKED -> {
                        likes -= 1
                        updateLikeStatus(LikeStatus.NEUTRAL, data)
                    }

                    LikeStatus.DISLIKED -> {
                        dislikes -= 1
                        updateLikeStatus(LikeStatus.LIKED, data)
                    }
                }
                updateLikeAndDislikeCounts()
            }

            dislikeIv.setOnClickListener {
                Log.i(TAG, "bind: dislike clicked")
                when (state) {
                    LikeStatus.NEUTRAL -> {
                        updateLikeStatus(LikeStatus.DISLIKED, data)
                    }

                    LikeStatus.LIKED -> {
                        likes -= 1
                        updateLikeStatus(LikeStatus.DISLIKED, data)
                    }

                    LikeStatus.DISLIKED -> {
                        dislikes -= 1
                        updateLikeStatus(LikeStatus.NEUTRAL, data)
                    }
                }
                updateLikeAndDislikeCounts()
            }
        }
    }

    private fun updateLikeStatus(newStatus: LikeStatus, data: QuestionData) {
        binding.apply {
            when (newStatus) {
                LikeStatus.LIKED -> {
                    likes += 1
                    likeIv.imageTintList = selectedColor
                }

                LikeStatus.NEUTRAL -> {
                    likeIv.imageTintList = neutralColor
                }

                LikeStatus.DISLIKED -> {
                    dislikes += 1
                    dislikeIv.imageTintList = selectedColor
                }
            }
        }
        state = newStatus
        viewModel.updateLikes(data, newStatus)
    }

    private fun updateLikeAndDislikeCounts() {
        binding.apply {
            likeTv.text = likes.toString()
            dislikeTv.text = dislikes.toString()
            likeIv.imageTintList = neutralColor
            dislikeIv.imageTintList = neutralColor
        }
    }

    private fun handleClick(data: Data) {
        val bundle = bundleOf(Common.KEY_QUESTION_ID to data)
        //binding.answerView.findNavController().navigate(R.id.action_questionListFragment_to_detailFragment, bundle)
    }
}

enum class LikeStatus {
    NEUTRAL,
    LIKED,
    DISLIKED
}
