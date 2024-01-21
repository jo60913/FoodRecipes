package com.example.foodrecipes.domain

import com.example.foodrecipes.data.IRepository
import com.example.foodrecipes.data.database.entity.FavoritesEntity
import dagger.hilt.android.scopes.ActivityRetainedScoped
import javax.inject.Inject

/**
 * 寫入笑話
 */
@ActivityRetainedScoped
class ReceiptInsert @Inject constructor(
    private val repository: IRepository
){
    suspend fun execute(favoritesEntity: FavoritesEntity) = repository.insertFavoriteRecipes(favoritesEntity)
}