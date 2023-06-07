package com.example.bettergeeks

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.navigation.findNavController
import com.example.bettergeeks.databinding.ActivityMainBinding
import com.example.bettergeeks.utils.Common.Companion.TAG
import com.google.android.material.navigation.NavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val actionBar = supportActionBar
        actionBar?.let {
            it.setDisplayHomeAsUpEnabled(true)
        }
        binding.navigationView.setNavigationItemSelectedListener(this)

        val actionBarDrawerToggle = ActionBarDrawerToggle(
            this,
            binding.drawerLayout,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        binding.drawerLayout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                binding.drawerLayout.openDrawer(GravityCompat.START)
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    fun setActionBar(view: View) {
        binding.toolbarLayout.removeAllViews()
        binding.toolbarLayout.addView(view)
        supportActionBar?.hide()
    }

    fun removeActionBar() {
        binding.toolbarLayout.visibility = View.VISIBLE
        binding.toolbarLayout.removeAllViews()
        supportActionBar?.show()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        Log.i(TAG, "onNavigationItemSelected: ")
        when (item.itemId) {
            R.id.nav_add_topic -> {
                findNavController(R.id.nav_host_fragment).navigate(R.id.action_topicFragment_to_addTopicFragment)
            }

            R.id.nav_ask_question -> {
                findNavController(R.id.nav_host_fragment).navigate(R.id.action_topicFragment_to_askQuestionFragment)
            }

            else -> {}
        }
        binding.drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }
}