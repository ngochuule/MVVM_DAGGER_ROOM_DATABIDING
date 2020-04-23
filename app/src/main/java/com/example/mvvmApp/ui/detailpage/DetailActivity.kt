package com.example.mvvmApp.ui.detailpage

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.mvvmApp.R
import com.example.mvvmApp.databinding.ActivityDetailBinding
import com.example.mvvmApp.ui.obtainViewModel
import com.example.mvvmApp.ui.replaceFragmentInActivity
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity(){

    private lateinit var viewModel: DetailViewModel

    fun obtainViewModel(): DetailViewModel = obtainViewModel(DetailViewModel::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = obtainViewModel()
        val binding =
            DataBindingUtil.setContentView<ActivityDetailBinding>(this, R.layout.activity_detail)
        binding.viewModel = viewModel

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        setupViewFragment()
    }

    private fun setupViewFragment() {
        supportFragmentManager.findFragmentById(R.id.contentFrame)
            ?: replaceFragmentInActivity(DetailFragment.newInstance(), R.id.contentFrame)
    }
}