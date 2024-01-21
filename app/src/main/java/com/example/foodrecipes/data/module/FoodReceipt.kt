package com.example.foodrecipes.data.module


import com.google.gson.annotations.SerializedName

data class FoodReceipt(
    @SerializedName("results")
    val results: List<Result>,
)