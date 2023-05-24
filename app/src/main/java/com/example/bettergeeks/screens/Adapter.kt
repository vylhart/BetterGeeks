package com.example.bettergeeks.screens

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.bettergeeks.databinding.CardTopicBinding

class Adapter(private val callback: (Data)->Unit) : ListAdapter<Data, MainViewHolder>(callbackHandler) {
    private var list = listOf(
        Data(0, "Android Dev"),
        Data(1, "Web Dev"),
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val binding = CardTopicBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MainViewHolder(binding, callback)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

}

class MainViewHolder(private val binding: CardTopicBinding, val callback: (Data) -> Unit) : RecyclerView.ViewHolder(binding.root) {
    fun bind(data: Data) {
        binding.textView.text = data.text
        binding.root.setOnClickListener {
            callback(data)
        }
    }

}

data class Data(
    val id: Long,
    val text: String,
)

private val callbackHandler = object : DiffUtil.ItemCallback<Data>() {
    override fun areItemsTheSame(oldItem: Data, newItem: Data): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Data, newItem: Data): Boolean {
        return oldItem == newItem
    }

}