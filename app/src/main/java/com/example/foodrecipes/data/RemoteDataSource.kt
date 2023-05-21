package com.example.foodrecipes.data

import com.example.foodrecipes.module.FoodJoke
import com.example.foodrecipes.module.FoodReceipt
import com.example.foodrecipes.network.FoodReceiptApi
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