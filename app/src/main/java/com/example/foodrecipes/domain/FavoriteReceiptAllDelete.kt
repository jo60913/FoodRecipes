package com.example.foodrecipes.domain

import com.example.foodrecipes.data.IRepository
import dagger.hilt.android.scopes.ActivityRetainedScoped
import javax.inject.Inject

@ActivityRetainedScoped
class FavoriteReceiptAllDelete @Inject constructor(
    private val repository: IRepository
){
    suspend fun execute() = repository.deleteAllFavoriteRecipes()
}