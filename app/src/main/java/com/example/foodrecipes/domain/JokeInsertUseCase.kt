package com.example.foodrecipes.domain

import com.example.foodrecipes.data.IRepository
import com.example.foodrecipes.data.database.entity.FoodJokeEntity
import dagger.hilt.android.scopes.ActivityRetainedScoped
import javax.inject.Inject

/**
 * 寫入笑話到資料庫
 */
@ActivityRetainedScoped
class JokeInsertUseCase @Inject constructor(
    private val repository: IRepository
){
    suspend fun execute(foodJokeEntity: FoodJokeEntity) = repository.insertFoodJoke(foodJokeEntity)
}