package com.example.foodrecipes.domain

import com.example.foodrecipes.data.IDataStoreRepository
import dagger.hilt.android.scopes.ActivityRetainedScoped
import javax.inject.Inject

@ActivityRetainedScoped
class MealAndDietTypeSaveUseCase @Inject constructor(
    private val dataStoreRepositoryImpl: IDataStoreRepository
) {
    suspend fun execute(
        mealType: String,
        mealTypeId: Int,
        dietType: String,
        dietTypeId: Int
    ) = dataStoreRepositoryImpl.saveMealAndDietType(
        mealType,
        mealTypeId,
        dietType,
        dietTypeId
    )
}