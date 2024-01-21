package com.example.foodrecipes.core.di

import com.example.foodrecipes.data.IRepository
import com.example.foodrecipes.data.Repository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun bindIRepository(implement: Repository): IRepository
}
