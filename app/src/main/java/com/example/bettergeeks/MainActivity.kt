package com.example.bettergeeks

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.bettergeeks.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //supportActionBar?.setDisplayHomeAsUpEnabled
        showActionBar()
    }

    fun showActionBar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.show()
    }

    fun hideActionBar() {
        setSupportActionBar(null)
        supportActionBar?.hide()
    }

}