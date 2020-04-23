package com.example.mvvmApp.ui.mainpage

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.mvvmApp.R
import com.example.mvvmApp.databinding.ActivityHomeBinding
import com.example.mvvmApp.ui.obtainViewModel
import com.example.mvvmApp.ui.replaceFragmentInActivity
import kotlinx.android.synthetic.main.activity_home.*

class HomeAcitivity : AppCompatActivity(){

    private lateinit var viewModel: HomeViewModel
    fun obtainViewModel(): HomeViewModel = obtainViewModel(HomeViewModel::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = obtainViewModel()
        val binding = DataBindingUtil.setContentView<ActivityHomeBinding>(this, R.layout.activity_home)
        binding.viewModel = viewModel
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        setupViewFragment()
    }

    private fun setupViewFragment() {
        supportFragmentManager.findFragmentById(R.id.contentFrame)
            ?: replaceFragmentInActivity(HomeFragment.newInstance(), R.id.contentFrame)
    }
}