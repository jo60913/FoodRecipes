package com.example.foodrecipes.network

import retrofit2.http.GET
import retrofit2.http.QueryMap
import com.example.foodrecipes.module.FoodReceipt
import retrofit2.Response

interface FoodReceiptApi{
    @GET("/recipes/complexSearch")
    suspend fun getFoodReceipt(
        @QueryMap queries:Map<String,String>
    ):Response<FoodReceipt>

    @GET("/recipes/complexSearch")
    suspend fun searchRecipes(
        @QueryMap searchQuery:Map<String,String>
    ):Response<FoodReceipt>
}