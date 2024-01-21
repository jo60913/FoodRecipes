package com.example.foodrecipes.domain

import com.example.foodrecipes.data.IRepository
import dagger.hilt.android.scopes.ActivityRetainedScoped
import javax.inject.Inject

/**
 * 使用關鍵字搜尋食譜
 */
@ActivityRetainedScoped
class SearchReceiptUseCase @Inject constructor(
    private val repository: IRepository,
) {
    suspend fun execute(searchQuery: Map<String, String>) = repository.getRecipes(searchQuery)
}