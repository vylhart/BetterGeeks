package com.kudesta.bettergeeks.screens.detail


import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.kudesta.bettergeeks.data.model.local.QuestionData
import com.kudesta.bettergeeks.databinding.FragmentDetailBinding
import com.kudesta.bettergeeks.utils.Common.KEY_QUESTION_ID
import com.kudesta.bettergeeks.utils.Common.TAG

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
            it.answer?.let { it1 -> binding.responseLayout.setText(it1) }
        }
    }
}