package com.example.foodrecipes.domain

import com.example.foodrecipes.data.IRepository
import com.example.foodrecipes.data.Repository
import dagger.hilt.android.scopes.ActivityRetainedScoped
import javax.inject.Inject

/**
 * 網路下載笑話
 */
@ActivityRetainedScoped
class DownloadFoodJoke @Inject constructor(
    private val repository: IRepository
) {
    suspend fun execute(apiKey : String) = repository.getFoodJoke(apiKey)
}