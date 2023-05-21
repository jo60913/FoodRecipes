package com.example.foodrecipes.di

import android.app.Application
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.foodrecipes.data.database.RecipeDatabase
import com.example.foodrecipes.util.Constants.Companion.DATABASE_NAME
import com.example.foodrecipes.util.Constants.Companion.FOOD_JOKE_TABLE
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(
        @ApplicationContext context: Context,
    ) = Room.databaseBuilder(
        context,
        RecipeDatabase::class.java,
        DATABASE_NAME
    ).addMigrations(object : Migration(1, 2) {
        override fun migrate(database: SupportSQLiteDatabase) {
            database.execSQL("CREATE TABLE IF NOT EXISTS ${FOOD_JOKE_TABLE} (`id` INTEGER PRIMARY KEY NOT NULL, `text` TEXT NOT NULL)")
        }
    }).build()

    @Singleton
    @Provides
    fun provideDao(database: RecipeDatabase) = database.recipesDao()
}