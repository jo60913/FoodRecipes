package com.example.foodrecipes.data

import androidx.lifecycle.LiveData
import com.example.foodrecipes.data.database.entity.FavoritesEntity
import com.example.foodrecipes.data.database.entity.FoodJokeEntity
import com.example.foodrecipes.module.FoodJoke
import com.example.foodrecipes.module.FoodReceipt
import retrofit2.Response
import com.example.foodrecipes.data.database.entity.ReceipesEntity
import kotlinx.coroutines.flow.Flow

interface IRepository {
    suspend fun getRecipes(queries: Map<String, String>): Response<FoodReceipt>
    suspend fun searchRecipes(searchQuery: Map<String, String>): Response<FoodReceipt>
    suspend fun getFoodJoke(apiKey: String): Response<FoodJoke>
    suspend fun insertRecipes(recipesEntity: ReceipesEntity)
    fun readRecipes(): Flow<List<ReceipesEntity>>
    fun readFavoriteRecipes(): Flow<List<FavoritesEntity>>
    suspend fun insertFavoriteRecipes(favoritesEntity: FavoritesEntity)
    suspend fun deleteFavoriteRecipe(favoritesEntity: FavoritesEntity)
    suspend fun deleteAllFavoriteRecipes()
    suspend fun insertFoodJoke(foodJokeEntity: FoodJokeEntity)
    fun readFoodJoke(): Flow<List<FoodJokeEntity>>
}
