package com.example.foodrecipes.data

import com.example.foodrecipes.data.database.RecipesDao
import com.example.foodrecipes.data.database.entity.FavoritesEntity
import com.example.foodrecipes.data.database.entity.FoodJokeEntity
import com.example.foodrecipes.data.database.entity.ReceipesEntity
import com.example.foodrecipes.data.module.FoodJoke
import com.example.foodrecipes.data.module.FoodReceipt
import com.example.foodrecipes.data.network.FoodReceiptApi
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Repository @Inject constructor(
    private val foodReceiptApi: FoodReceiptApi,
    private val recipesDao: RecipesDao
) : IRepository {
    override suspend fun getRecipes(queries: Map<String, String>): Response<FoodReceipt> {
        return foodReceiptApi.getFoodReceipt(queries)
    }

    override suspend fun searchRecipes(searchQuery: Map<String, String>): Response<FoodReceipt> {
        return foodReceiptApi.searchRecipes(searchQuery)
    }

    override suspend fun getFoodJoke(apiKey: String): Response<FoodJoke> {
        return foodReceiptApi.getFoodJoke(apiKey)
    }

    override suspend fun insertRecipes(recipesEntity: ReceipesEntity) {
        recipesDao.insertRecipes(recipesEntity)
    }

    override fun readRecipes() = recipesDao.readRecipes()

    override fun readFavoriteRecipes(): Flow<List<FavoritesEntity>> = recipesDao.readFavoriteRecipes()

    override suspend fun insertFavoriteRecipes(favoritesEntity: FavoritesEntity) =
        recipesDao.insertFavoriteRecipes(favoritesEntity)

    override suspend fun deleteFavoriteRecipe(favoritesEntity: FavoritesEntity) =
        recipesDao.deleteFavoriteRecipes(favoritesEntity)

    override suspend fun deleteAllFavoriteRecipes() = recipesDao.deleteAllFavoriteRecipes()

    override suspend fun insertFoodJoke(foodJokeEntity: FoodJokeEntity) =
        recipesDao.insertFoodJoke(foodJokeEntity)


    override fun readFoodJoke(): Flow<List<FoodJokeEntity>> =
        recipesDao.readFoodjoke()
}