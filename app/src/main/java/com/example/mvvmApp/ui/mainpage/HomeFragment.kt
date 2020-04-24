package com.example.mvvmApp.ui.mainpage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.mvvmApp.databinding.FragmentHomeBinding
import com.example.mvvmApp.ui.BaseFragment

class HomeFragment: BaseFragment() {

    private lateinit var viewDataBinding: FragmentHomeBinding
    private lateinit var viewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        viewModel = (activity as HomeActivity).obtainViewModel()
        viewDataBinding = FragmentHomeBinding.inflate(inflater, container, false)
        viewDataBinding.viewModel = viewModel

        return viewDataBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewDataBinding.lifecycleOwner = viewLifecycleOwner
    }

    companion object {
        fun newInstance() = HomeFragment()
    }

}