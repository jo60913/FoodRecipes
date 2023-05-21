package com.example.foodrecipes.network

import com.example.foodrecipes.module.FoodJoke
import retrofit2.http.GET
import retrofit2.http.QueryMap
import com.example.foodrecipes.module.FoodReceipt
import retrofit2.Response
import retrofit2.http.Query

interface FoodReceiptApi{
    @GET("/recipes/complexSearch")
    suspend fun getFoodReceipt(
        @QueryMap queries:Map<String,String>
    ):Response<FoodReceipt>

    @GET("/recipes/complexSearch")
    suspend fun searchRecipes(
        @QueryMap searchQuery:Map<String,String>
    ):Response<FoodReceipt>

    @GET("food/jokes/random")
    suspend fun getFoodJoke(
        @Query("apiKey") apiKey:String
    ):Response<FoodJoke>
}