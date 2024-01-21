package com.example.foodrecipes.presentation.ui.fragment.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.foodrecipes.databinding.FragmentFoodjokeBinding
import com.example.foodrecipes.core.util.Constants.Companion.API_KEY
import com.example.foodrecipes.core.util.NetworkResult
import com.example.foodrecipes.presentation.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FoodjokeFragment : Fragment() {

    private val mainViewModel by viewModels<MainViewModel>()

    private var _binding:FragmentFoodjokeBinding? = null
    private val binding get() = this._binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFoodjokeBinding.inflate(inflater,container,false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.mainViewModel = this.mainViewModel
        mainViewModel.getFoodJoke(API_KEY)
        mainViewModel.foodJokeResponse.observe(viewLifecycleOwner){
            when(it){
                is NetworkResult.Success->{
                    binding.foodJokeTextView.text = it.data?.text
                }

                is NetworkResult.Loading->{

                }

                is NetworkResult.Error->{
                    readJokeFromDatabase()
                    Toast.makeText(
                        requireContext(),
                        it.message.toString(),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
        return binding.root
    }
    private fun readJokeFromDatabase(){
        lifecycleScope.launch{
            mainViewModel.readFoodJoke.observe(viewLifecycleOwner){
                if(!it.isNullOrEmpty()){
                    binding.foodJokeTextView.text = it[0].foodJoke.text
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}