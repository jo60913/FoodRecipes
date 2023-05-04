package com.example.foodrecipes.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foodrecipes.viewmodel.MainViewModel
import com.example.foodrecipes.R
import com.example.foodrecipes.adapter.ReceiptAdapter
import com.example.foodrecipes.databinding.FragmentRecipesBinding
import com.example.foodrecipes.util.Constants.Companion.API_KEY
import com.example.foodrecipes.util.NetworkResult
import com.example.foodrecipes.util.observeOnce
import com.example.foodrecipes.viewmodel.RecipesViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RecipesFragment : Fragment() {
    private var _binding:FragmentRecipesBinding? = null
    private val binding get() = _binding!!

    private lateinit var mainviewmodel : MainViewModel
    private lateinit var receipesViewModel: RecipesViewModel
    private val mAdapter by lazy { ReceiptAdapter() }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRecipesBinding.inflate(inflater,container,false)
        binding.lifecycleOwner = this
        binding.mainViewModel = mainviewmodel

        setRecyclerView()
        readDatabase()
        return binding.root
    }

    private fun readDatabase() {
        lifecycleScope.launch{
            mainviewmodel.readRecipes.observeOnce(viewLifecycleOwner) { database ->
                if (database.isNotEmpty()) {
                    mAdapter.setData(database[0].foodReceipt)
                }else{
                    requestApiData()
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainviewmodel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
        receipesViewModel = ViewModelProvider(requireActivity()).get(RecipesViewModel::class.java)
    }

    private fun setRecyclerView(){
        binding.recyclerview.adapter = mAdapter
        binding.recyclerview.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun requestApiData(){
        mainviewmodel.getRecipes(receipesViewModel.allyQuery())
        mainviewmodel.recipesResponse.observe(viewLifecycleOwner) { response ->
            when (response) {
                is NetworkResult.Success -> {
                    response.data?.let {
                        mAdapter.setData(it)
                    }
                }
                is NetworkResult.Error -> {
                    loadDataFromCache() //如果資料有錯誤就顯示載入前的畫面
                    Toast.makeText(
                        requireContext(),
                        response.message.toString(),
                        Toast.LENGTH_SHORT
                    ).show()
                }
                is NetworkResult.Loading -> {

                }

            }
        }
    }

    private fun loadDataFromCache(){
        lifecycleScope.launch{
            mainviewmodel.readRecipes.observe(viewLifecycleOwner){ database ->
                if(database.isNotEmpty()){
                    mAdapter.setData(database[0].foodReceipt)
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}