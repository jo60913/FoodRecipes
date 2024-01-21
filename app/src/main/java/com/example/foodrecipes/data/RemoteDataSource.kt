package com.example.foodrecipes.data

import com.example.foodrecipes.data.module.FoodJoke
import com.example.foodrecipes.data.module.FoodReceipt
import com.example.foodrecipes.data.network.FoodReceiptApi
import retrofit2.Response
import javax.inject.Inject

class RemoteDataSource@Inject constructor(
    private val foodReceiptApi: FoodReceiptApi
) {
    suspend fun getRecipes(queries:Map<String,String>):Response<FoodReceipt>{
        return foodReceiptApi.getFoodReceipt(queries)
    }

    suspend fun searchRecipes(searchQuery:Map<String,String>):Response<FoodReceipt>{
        return foodReceiptApi.searchRecipes(searchQuery)
    }

    suspend fun getFoodJoke(apiKey:String):Response<FoodJoke>{
        return foodReceiptApi.getFoodJoke(apiKey)
    }
}