package com.example.foodrecipes.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.foodrecipes.data.database.entity.FavoritesEntity
import com.example.foodrecipes.data.database.entity.ReceipesEntity

@Database(
    entities = [ReceipesEntity::class,FavoritesEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(RecipesTypeConverter::class)
abstract class RecipeDatabase : RoomDatabase(){
    abstract fun recipesDao(): RecipesDao
}