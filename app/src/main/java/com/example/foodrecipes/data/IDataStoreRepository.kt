package com.example.foodrecipes.data

interface IDataStoreRepository {
    suspend fun saveMealAndDietType(
        mealType: String,
        mealTypeId: Int,
        dietType: String,
        dietTypeId: Int
    )

    suspend fun saveBackOnline(backonline: Boolean)
}