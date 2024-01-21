package com.example.foodrecipes.data

import com.example.foodrecipes.data.database.entity.ReceipesEntity
import com.example.foodrecipes.data.database.RecipesDao
import com.example.foodrecipes.data.database.entity.FavoritesEntity
import com.example.foodrecipes.data.database.entity.FoodJokeEntity
import com.example.foodrecipes.data.module.FoodJoke
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalDataSource @Inject constructor(
    private val recipesDao: RecipesDao
) {



}