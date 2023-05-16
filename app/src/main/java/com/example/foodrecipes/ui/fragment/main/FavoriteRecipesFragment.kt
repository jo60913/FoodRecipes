package com.example.foodrecipes.ui.fragment.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.foodrecipes.R
import com.example.foodrecipes.adapter.FavoriteRecipesAdapter
import com.example.foodrecipes.databinding.FragmentFavoriteRecipesBinding
import com.example.foodrecipes.databinding.FragmentRecipesBinding
import com.example.foodrecipes.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteRecipesFragment : Fragment() {

    private val mAdapter : FavoriteRecipesAdapter by lazy { FavoriteRecipesAdapter(requireActivity()) }
    private val mainViewModel :MainViewModel by viewModels()
    private var _binding: FragmentFavoriteRecipesBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFavoriteRecipesBinding.inflate(layoutInflater)
        binding.lifecycleOwner = this
        binding.mainViewModel = mainViewModel
        binding.mAdapter = mAdapter

        setupRecyclerView(binding.favoriteRecipesRecyclerView)

        return binding.root
    }

    private fun setupRecyclerView(recyclerView: RecyclerView){
        recyclerView.adapter = mAdapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        this._binding = null
    }
}