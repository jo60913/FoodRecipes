package com.example.foodrecipes.presentation.ui.fragment.main

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foodrecipes.presentation.viewmodel.MainViewModel
import com.example.foodrecipes.R
import com.example.foodrecipes.presentation.adapter.ReceiptAdapter
import com.example.foodrecipes.databinding.FragmentRecipesBinding
import com.example.foodrecipes.core.util.Constants.Companion.SEARCH_KEYWORD_FROM_DATABASE
import com.example.foodrecipes.core.util.Constants.Companion.SEARCH_RECIPE_FROM_SEARCHBAR
import com.example.foodrecipes.core.util.NetworkListener
import com.example.foodrecipes.core.util.NetworkResult
import com.example.foodrecipes.core.util.observeOnce
import com.example.foodrecipes.presentation.viewmodel.RecipesViewModel
import com.google.firebase.analytics.FirebaseAnalytics
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RecipesFragment : Fragment() {
    private val args by navArgs<RecipesFragmentArgs>()  //RecipesFragmentArgs是由navigation自動產生的

    private var _binding: FragmentRecipesBinding? = null
    private val binding get() = _binding!!

    private lateinit var mainviewmodel : MainViewModel
    private lateinit var receipesViewModel: RecipesViewModel
    private val mAdapter by lazy { ReceiptAdapter() }
    private lateinit var networkListener: NetworkListener
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecipesBinding.inflate(inflater,container,false)
        binding.lifecycleOwner = this
        binding.mainViewModel = mainviewmodel

        setRecyclerView()
        receipesViewModel.readBackOnline.observe(viewLifecycleOwner){
            receipesViewModel.backOnline = it
        }
        setHasOptionsMenu(true)
        binding.recipesFab.setOnClickListener {
            if(receipesViewModel.networkStatus)
                findNavController().navigate(R.id.action_recipesFragment_to_recipesBottomSheet)
            else
                receipesViewModel.showNetworkStatus()
        }
        lifecycleScope.launch{
            networkListener = NetworkListener()
            networkListener.checkNetworkAvailable(requireContext())     //只要網路變化就會讀取資料庫
                .collect{
                    Log.e("網路變化",it.toString())
                    receipesViewModel.networkStatus = it
                    receipesViewModel.showNetworkStatus()
                    readDatabase()
                }
        }

        return binding.root
    }


    private fun readDatabase() {
        lifecycleScope.launch{
            mainviewmodel.readRecipes.observeOnce(viewLifecycleOwner) { database ->
                if (database.isNotEmpty() && !args.backFromBottomSheet) {
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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.recipes_menu,menu)

        val search = menu.findItem(R.id.search)
        val searchView = search.actionView as? SearchView
        searchView?.isSubmitButtonEnabled = true
        searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                if(query != null) {
                    searchApiData(query)
                    setSearchDataToFirebase(query)
                }

                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }

        })
    }

    private fun setRecyclerView(){
        binding.recyclerview.adapter = mAdapter
        binding.recyclerview.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun setSearchDataToFirebase(query:String){
        val firebaseData = Bundle()
        firebaseData.putString(SEARCH_KEYWORD_FROM_DATABASE,query)
        FirebaseAnalytics.getInstance(requireContext()).logEvent(SEARCH_RECIPE_FROM_SEARCHBAR,firebaseData)
    }

    private fun searchApiData(searchQuery:String){
        mainviewmodel.searchRecipes(receipesViewModel.applySearchQuery(searchQuery))
        mainviewmodel.searchRecipesResponse.observe(viewLifecycleOwner){ response ->
            when(response){
                is NetworkResult.Success ->{
                    val foodReceipt = response.data
                    foodReceipt?.let { it -> mAdapter.setData(it) }
                }
                is NetworkResult.Error ->{
                    loadDataFromCache()
                    Toast.makeText(requireContext(),response.message.toString(),Toast.LENGTH_LONG).show()
                }
                is NetworkResult.Loading ->{

                }
            }
        }
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