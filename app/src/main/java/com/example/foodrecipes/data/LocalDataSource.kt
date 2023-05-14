package com.example.foodrecipes.data

import com.example.foodrecipes.data.database.entity.ReceipesEntity
import com.example.foodrecipes.data.database.RecipesDao
import com.example.foodrecipes.data.database.entity.FavoritesEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalDataSource @Inject constructor(
    private val recipesDao: RecipesDao
) {

    suspend fun insertRecipes(recipesEntity : ReceipesEntity){
        recipesDao.insertRecipes(recipesEntity)
    }

    fun readRecipes() = recipesDao.readRecipes()

    fun readFacoriteRecipes(): Flow<List<FavoritesEntity>>
        = recipesDao.readFavoriteRecipes()

    suspend fun insertFacoriteRecipes(favoritesEntity: FavoritesEntity)
        = recipesDao.insertFavoriteRecipes(favoritesEntity)

    suspend fun deleteFavoriteRecipe(favoritesEntity: FavoritesEntity)
        = recipesDao.deleteFavoriteRecipes(favoritesEntity)

    suspend fun deleteAllFavoriteRecipes(){
        recipesDao.deleteAllFavoriteRecipes()
    }
}