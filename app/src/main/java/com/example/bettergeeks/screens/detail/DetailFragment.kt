package com.example.bettergeeks.screens.detail


import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.example.bettergeeks.MainActivity
import com.example.bettergeeks.data.model.local.QuestionData
import com.example.bettergeeks.databinding.FragmentDetailBinding
import com.example.bettergeeks.utils.Common.Companion.KEY_QUESTION_ID
import com.example.bettergeeks.utils.Common.Companion.TAG

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
class DetailFragment : Fragment() {
    private lateinit var binding: FragmentDetailBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?, ): View {
        binding = FragmentDetailBinding.inflate(inflater, container, false)
        setUpView()
        return binding.root
    }

    private fun setUpView() {
        arguments?.getParcelable(KEY_QUESTION_ID, QuestionData::class.java)?.let {
            Log.i(TAG, "setUpView: $it")
            binding.questionText.text = it.question
            binding.answerText.text = it.answer
        }
    }

    override fun onDestroyView() {
        (activity as? MainActivity)?.removeActionBar()
        super.onDestroyView()
    }


}