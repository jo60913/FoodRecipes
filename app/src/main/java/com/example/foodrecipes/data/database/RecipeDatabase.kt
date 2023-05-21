package com.example.foodrecipes.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.foodrecipes.data.database.entity.FavoritesEntity
import com.example.foodrecipes.data.database.entity.FoodJokeEntity
import com.example.foodrecipes.data.database.entity.ReceipesEntity

@Database(
    entities = [ReceipesEntity::class,FavoritesEntity::class,FoodJokeEntity::class],
    version = 2,
    exportSchema = true
)
@TypeConverters(RecipesTypeConverter::class)
abstract class RecipeDatabase : RoomDatabase(){
    abstract fun recipesDao(): RecipesDao

}