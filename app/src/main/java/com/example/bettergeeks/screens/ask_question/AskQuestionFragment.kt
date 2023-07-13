package com.example.bettergeeks.screens.ask_question

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.bettergeeks.R
import com.example.bettergeeks.data.model.local.TopicData
import com.example.bettergeeks.databinding.FragmentAskQuestionBinding
import com.example.bettergeeks.screens.add_topic.AddTopicFragment
import com.example.bettergeeks.screens.recycler.ResponseView
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class AskQuestionFragment : Fragment() {

    private lateinit var binding: FragmentAskQuestionBinding
    private val viewModel: AskQuestionViewModel by viewModels()
    private var menuList: Menu? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentAskQuestionBinding.inflate(inflater, container, false)
        setObservers()
        setHasOptionsMenu(true)
        return binding.root
    }

    private fun setObservers() {
        binding.editTextQuestion.setOnEditorActionListener { _, _, _ ->
            val question = binding.editTextQuestion.text.toString().trim()
            viewModel.generateAnswer(question)
            true
        }

        viewModel.list.observe(viewLifecycleOwner) {
            binding.spinner.adapter = ArrayAdapter(
                requireContext(),
                R.layout.spinner_item,
                it.map { topicData -> topicData.topicName })
            binding.spinner.onItemSelectedListener = getSpinnerListener(it)
        }

        viewModel.textResponse.observe(viewLifecycleOwner) {
            when (it) {
                is ResponseData.Success -> {
                    binding.responseLayout.apply {
                        if (it.data.answer != null && it.data.question != null) {
                            setText(it.data.answer)
                            setQuestion(it.data.question)
                            //setClickListener { viewModel.save(it.data) }
                        }
                    }
                    menuList?.findItem(R.id.nav_save)?.isVisible = true
                }

                is ResponseData.Error -> {
                    showToast(it.message)
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        this.menuList = menu
        inflater.inflate(R.menu.ask_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.nav_save -> {
                viewModel.save()
            }
            R.id.nav_add_topic -> {
                AddTopicFragment().show(parentFragmentManager, "AddTopicFragment")
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showToast(message: String) {
        Toast.makeText(requireActivity(), message, Toast.LENGTH_SHORT).show()
    }

    private fun getSpinnerListener(topicData: List<TopicData>): AdapterView.OnItemSelectedListener {
        return object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val selectedTopic = parent?.getItemAtPosition(position) as String
                viewModel.selectedTopic = topicData.find { it.topicName == selectedTopic }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                viewModel.selectedTopic = null
            }
        }
    }
}