package com.example.foodrecipes.domain

import com.example.foodrecipes.data.IRepository
import com.example.foodrecipes.data.database.entity.FavoritesEntity
import dagger.hilt.android.scopes.ActivityRetainedScoped
import javax.inject.Inject

/**
 * 刪除我的最愛的食譜
 */
@ActivityRetainedScoped
class FavoriteReceiptDeleteUseCase @Inject constructor(
    private val repository: IRepository
) {
    suspend fun execute(favoritesEntity: FavoritesEntity) = repository.deleteFavoriteRecipe(favoritesEntity)
}