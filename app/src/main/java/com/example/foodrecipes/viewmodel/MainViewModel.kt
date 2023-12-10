package com.example.foodrecipes.viewmodel

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.lifecycle.*
import com.example.foodrecipes.data.Repository
import com.example.foodrecipes.data.database.entity.FavoritesEntity
import com.example.foodrecipes.data.database.entity.FoodJokeEntity
import com.example.foodrecipes.data.database.entity.ReceipesEntity
import com.example.foodrecipes.module.FoodJoke
import com.example.foodrecipes.module.FoodReceipt
import com.example.foodrecipes.util.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: Repository,
    application: Application
):AndroidViewModel(application) {

    /** Room Database */
    val readRecipes:LiveData<List<ReceipesEntity>> = repository.readRecipes().asLiveData()
    val readFavoriteRecipes:LiveData<List<FavoritesEntity>> = repository.readFavoriteRecipes().asLiveData()
    val readFoodJoke:LiveData<List<FoodJokeEntity>> = repository.readFoodJoke().asLiveData()
    private fun insertRecipes(receipesEntity: ReceipesEntity) =
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertRecipes(receipesEntity)
        }

    /** Retrofit */
    var recipesResponse : MutableLiveData<NetworkResult<FoodReceipt>> = MutableLiveData()
    var searchRecipesResponse : MutableLiveData<NetworkResult<FoodReceipt>> = MutableLiveData()
    var foodjokeResponse :MutableLiveData<NetworkResult<FoodJoke>> = MutableLiveData()
    fun getRecipes(queries : Map<String,String>) = viewModelScope.launch {
        getReceipesSafeCall(queries)
    }

    fun searchRecipes(searchQuery:Map<String,String>) = viewModelScope.launch {
        searchRecipesSafeCall(searchQuery)
    }

    fun getFoodJoke(apikey:String) = viewModelScope.launch{
        getfoodjokeSafeCall(apikey)
    }

    private suspend fun getfoodjokeSafeCall(apikey: String) {
        foodjokeResponse.value = NetworkResult.Loading()
        if(hasInternetConnection()){
            try {
                val response = repository.getFoodJoke(apikey)
                foodjokeResponse.value = handleFoodJokeResponse(response)

                val foodjoke = foodjokeResponse.value!!.data
                if(foodjoke != null)
                    offlineCacheFoodjoke(foodjoke)
            }catch (e:java.lang.Exception){
                foodjokeResponse.value = NetworkResult.Error("找不到食譜")
            }
        }else{
            foodjokeResponse.value = NetworkResult.Error("沒有網路")
        }
    }

    private suspend fun searchRecipesSafeCall(searchQuery: Map<String, String>) {
        searchRecipesResponse.value = NetworkResult.Loading()
        if(hasInternetConnection()){
            try {
                val response = repository.searchRecipes(searchQuery)
                searchRecipesResponse.value = handleFoodRecipesresponse(response)
            }catch (e:java.lang.Exception){
                searchRecipesResponse.value = NetworkResult.Error("找不到食譜")
            }
        }else{
            searchRecipesResponse.value = NetworkResult.Error("沒有網路")
        }
    }

    private suspend fun getReceipesSafeCall(queries: Map<String, String>) {
        recipesResponse.value = NetworkResult.Loading()
        if(hasInternetConnection()){
            try {
                val response = repository.getRecipes(queries)
                recipesResponse.value = handleFoodRecipesresponse(response)

                val foodReceipt = recipesResponse.value!!.data
                if(foodReceipt != null)
                    offlineCacheRecipes(foodReceipt)
            }catch (e:java.lang.Exception){
                recipesResponse.value = NetworkResult.Error("找不到食譜")
            }
        }else{
            recipesResponse.value = NetworkResult.Error("沒有網路")
        }
    }

    private fun handleFoodJokeResponse(response: Response<FoodJoke>): NetworkResult<FoodJoke>? {
        return when {
            response.message().toString().contains("timeout") -> {
                NetworkResult.Error("Timeout")
            }
            response.code() == 402 -> {
                NetworkResult.Error("API Key Limited.")
            }
            response.isSuccessful -> {
                val foodJoke = response.body()
                NetworkResult.Success(foodJoke!!)
            }
            else -> {
                NetworkResult.Error(response.message())
            }
        }
    }

    private fun offlineCacheRecipes(foodReceipt: FoodReceipt) {
        val recipesEntity = ReceipesEntity(foodReceipt)
        insertRecipes(recipesEntity)
    }

    private fun offlineCacheFoodjoke(foodJoke: FoodJoke) {
        val foodjoke  = FoodJokeEntity(foodJoke)
        insertFoodJoke(foodjoke)
    }

    /**
     * 把回傳的資料轉為NetworkResult
     */
    private fun handleFoodRecipesresponse(response: Response<FoodReceipt>): NetworkResult<FoodReceipt>? {
        when{
            response.message().toString().contains("timeout")->{
                return NetworkResult.Error("超時")
            }
            response.code() == 402->{
                return NetworkResult.Error("api key過期")
            }
            response.body()!!.results.isNullOrEmpty()->{
                return NetworkResult.Error("找不到食譜")
            }
            response.isSuccessful ->{
                val foodReceipt = response.body()
                return NetworkResult.Success(foodReceipt)
            }
            else ->{
                return NetworkResult.Error(response.message())
            }
        }
    }

    //取的網路狀況有網路為true
    private fun hasInternetConnection():Boolean{
        val connectivityManager = getApplication<Application>().getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager
        val activityNetwork = connectivityManager.activeNetwork?:return false
        val capabilities = connectivityManager.getNetworkCapabilities(activityNetwork) ?: return false
        return when{
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
    }

    fun insertFacoriteRecipes(favoritesEntity: FavoritesEntity) =
        viewModelScope.launch (Dispatchers.IO) {
            repository.insertFavoriteRecipes(favoritesEntity)
        }

    fun insertFoodJoke(foodJokeEntity:FoodJokeEntity) =
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertFoodJoke(foodJokeEntity)
        }

    fun deleteFacoriteRecipes(favoritesEntity: FavoritesEntity) =
        viewModelScope.launch (Dispatchers.IO) {
            repository.deleteFavoriteRecipe(favoritesEntity)
        }

    fun deleteAllFacoriteRecipes() =
        viewModelScope.launch (Dispatchers.IO) {
            repository.deleteAllFavoriteRecipes()
        }
}