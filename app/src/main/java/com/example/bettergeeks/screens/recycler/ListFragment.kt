package com.example.bettergeeks.screens.recycler

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bettergeeks.databinding.FragmentListBinding

abstract class ListFragment : Fragment() {
    protected lateinit var binding: FragmentListBinding
    protected lateinit var adapter: Adapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?, ): View {
        binding = FragmentListBinding.inflate(inflater, container, false)
        setupRecyclerView()
        init()
        return binding.root
    }

    private fun setupRecyclerView() {
        adapter = Adapter(activity)
        binding.recyclerView.layoutManager = LinearLayoutManager(activity)
        binding.recyclerView.adapter = adapter
    }

    abstract fun init()

}