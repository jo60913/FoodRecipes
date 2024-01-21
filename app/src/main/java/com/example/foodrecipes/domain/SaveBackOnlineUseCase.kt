package com.example.foodrecipes.domain

import com.example.foodrecipes.data.IDataStoreRepository
import dagger.hilt.android.scopes.ActivityRetainedScoped
import javax.inject.Inject

@ActivityRetainedScoped
class SaveBackOnlineUseCase @Inject constructor(
    private val dataStoreRepositoryImpl: IDataStoreRepository
){
    suspend fun execute(backOnline: Boolean) = dataStoreRepositoryImpl.saveBackOnline(backOnline)
}