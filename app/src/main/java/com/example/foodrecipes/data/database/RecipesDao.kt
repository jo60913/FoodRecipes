package com.example.foodrecipes.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.foodrecipes.data.database.entity.FavoritesEntity
import com.example.foodrecipes.data.database.entity.FoodJokeEntity
import com.example.foodrecipes.data.database.entity.ReceipesEntity
import com.example.foodrecipes.data.module.FoodJoke
import kotlinx.coroutines.flow.Flow


@Dao
interface RecipesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecipes(receipesEntity: ReceipesEntity)

    @Query("SELECT *  FROM recipes_table ORDER BY ID ASC")
    fun readRecipes(): Flow<List<ReceipesEntity>>

//    FavoritesTable
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavoriteRecipes(favoritesEntity: FavoritesEntity)

    @Query("SELECT * FROM favorite_recipes_table ORDER BY id ASC")
    fun readFavoriteRecipes():Flow<List<FavoritesEntity>>

    @Delete
    suspend fun deleteFavoriteRecipes(favoritesEntity: FavoritesEntity)

    @Query("DELETE FROM favorite_recipes_table")
    suspend fun deleteAllFavoriteRecipes()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFoodJoke(foodJokeEntity: FoodJokeEntity)

    @Query("SELECT * FROM FOOD_JOKE_TABLE ORDER BY id ASC")
    fun readFoodjoke():Flow<List<FoodJokeEntity>>
}