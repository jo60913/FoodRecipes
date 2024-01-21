package com.example.foodrecipes.presentation.ui.fragment.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foodrecipes.presentation.adapter.IngredientsAdapter
import com.example.foodrecipes.databinding.FragmentIngredientsBinding
import com.example.foodrecipes.data.module.Result
import com.example.foodrecipes.core.util.Constants


class IngredientsFragment : Fragment() {
    private val mAdapter by lazy { IngredientsAdapter() }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentIngredientsBinding.inflate(inflater,container,false)

        val args = arguments
        val myBundle : Result? = args?.getParcelable<Result>(Constants.RECIPES_BUNDLE_KEY)
        myBundle?.extendedIngredients?.let {
            mAdapter.setData(it)
        }
        setupRecyclerView(binding)
        return binding.root
    }

    private fun setupRecyclerView(binding:FragmentIngredientsBinding){
        binding.recyclerview.adapter = mAdapter
        binding.recyclerview.layoutManager = LinearLayoutManager(binding.root.context)
    }
}