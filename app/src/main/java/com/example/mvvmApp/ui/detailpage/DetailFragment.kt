package com.example.mvvmApp.ui.detailpage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.mvvmApp.databinding.FragmentDetailBinding
import com.example.mvvmApp.ui.BaseFragment

class DetailFragment : BaseFragment(){

    private lateinit var viewDataBinding: FragmentDetailBinding
    private lateinit var viewModel: DetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        viewModel = (activity as DetailActivity).obtainViewModel()
        viewDataBinding = FragmentDetailBinding.inflate(inflater, container, false)
        viewDataBinding.viewModel = viewModel

        return viewDataBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewDataBinding.lifecycleOwner = viewLifecycleOwner
    }

    companion object {
        fun newInstance() = DetailFragment()
    }
}