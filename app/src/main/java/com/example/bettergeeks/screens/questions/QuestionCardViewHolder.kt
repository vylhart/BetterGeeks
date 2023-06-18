package com.example.bettergeeks.screens.questions

import android.util.Log
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.bettergeeks.MainActivity
import com.example.bettergeeks.R
import com.example.bettergeeks.data.model.local.QuestionData
import com.example.bettergeeks.databinding.CardQuestionBinding
import com.example.bettergeeks.screens.recycler.Data
import com.example.bettergeeks.utils.Common
import com.example.bettergeeks.utils.Common.TAG

class QuestionCardViewHolder(private val binding: CardQuestionBinding, activity: FragmentActivity?) :
    RecyclerView.ViewHolder(binding.root) {

    private var state = LikeStatus.NEUTRAL
    var likes = 0
    var dislikes = 0

    private val viewModel: QuestionViewModel by lazy {
        ViewModelProvider(activity as MainActivity)[QuestionViewModel::class.java]
    }

    fun bind(data: QuestionData) {
        binding.apply {
            answerView.setOnClickListener { handleClick(data) }
            imageViewText.text = data.question
            answerView.text = data.answer
            likeTv.text = data.likes.toString()
            dislikeTv.text = data.dislikes.toString()
            if(data.isLiked) {
                likeIv.imageTintList = ContextCompat.getColorStateList(likeIv.context, R.color.blue)
                state = LikeStatus.LIKED
            } else if(data.isDisliked) {
                dislikeIv.imageTintList = ContextCompat.getColorStateList(likeIv.context, R.color.white)
                state = LikeStatus.DISLIKED
            }
            likes = data.likes
            dislikes = data.dislikes
            imageViewText.background = ContextCompat.getDrawable(imageViewText.context, Common.randomColor())
        }
        likesListener(data)
    }

    fun likesListener(data: QuestionData) {
        binding.apply {
            likeIv.setOnClickListener {
                Log.i(TAG, "bind: like clicked")
                when(state) {
                    LikeStatus.NEUTRAL -> {
                        likes += 1
                        viewModel.updateLikes(data, LikeStatus.LIKED)
                        state = LikeStatus.LIKED
                        likeIv.imageTintList = ContextCompat.getColorStateList(it.context, R.color.blue)

                    }
                    LikeStatus.LIKED -> {
                        likes -= 1
                        viewModel.updateLikes(data, LikeStatus.NEUTRAL)
                        state = LikeStatus.NEUTRAL
                        likeIv.imageTintList = ContextCompat.getColorStateList(it.context, R.color.white)
                    }
                    LikeStatus.DISLIKED -> {
                        dislikes -= 1
                        likes += 1
                        state = LikeStatus.LIKED
                        viewModel.updateLikes(data, LikeStatus.LIKED)
                        likeIv.imageTintList = ContextCompat.getColorStateList(it.context, R.color.blue)
                    }

                }
                likeTv.text = likes.toString()
                dislikeTv.text = dislikes.toString()
                dislikeIv.imageTintList = ContextCompat.getColorStateList(it.context, R.color.white)
            }

            dislikeIv.setOnClickListener {
                Log.i(TAG, "bind: dislike clicked")
                when(state) {
                    LikeStatus.NEUTRAL -> {
                        dislikes += 1
                        state = LikeStatus.DISLIKED
                        viewModel.updateLikes(data, LikeStatus.DISLIKED)
                        dislikeIv.imageTintList = ContextCompat.getColorStateList(it.context, R.color.blue)
                    }
                    LikeStatus.LIKED -> {
                        likes -= 1
                        dislikes += 1
                        state = LikeStatus.DISLIKED
                        viewModel.updateLikes(data, LikeStatus.DISLIKED)
                        dislikeIv.imageTintList = ContextCompat.getColorStateList(it.context, R.color.blue)
                    }
                    LikeStatus.DISLIKED -> {
                        dislikes -= 1
                        state = LikeStatus.NEUTRAL
                        viewModel.updateLikes(data, LikeStatus.NEUTRAL)
                        dislikeIv.imageTintList = ContextCompat.getColorStateList(it.context, R.color.white)
                    }
                }
                likeTv.text = likes.toString()
                dislikeTv.text = dislikes.toString()
                likeIv.imageTintList = ContextCompat.getColorStateList(it.context, R.color.white)
            }

        }
    }

    private fun handleClick(data: Data) {
        val bundle = bundleOf(Common.KEY_QUESTION_ID to data)
        binding.answerView.findNavController().navigate(R.id.action_questionListFragment_to_detailFragment, bundle)
    }
}

enum class LikeStatus {
    NEUTRAL,
    LIKED,
    DISLIKED
}
