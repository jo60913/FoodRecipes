package com.example.foodrecipes.data

import androidx.room.Dao
import com.example.foodrecipes.data.database.ReceipesEntity
import com.example.foodrecipes.data.database.RecipesDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalDataSource @Inject constructor(
    private val recipesDao: RecipesDao
) {

    suspend fun insertRecipes(recipesEntity : ReceipesEntity){
        recipesDao.insertRecipes(recipesEntity)
    }

    fun readDatabase() = recipesDao.readRecipes()
}