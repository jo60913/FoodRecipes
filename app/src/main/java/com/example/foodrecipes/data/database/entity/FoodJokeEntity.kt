package com.example.foodrecipes.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.foodrecipes.data.module.FoodJoke
import com.example.foodrecipes.core.util.Constants.Companion.FOOD_JOKE_TABLE
import com.google.gson.annotations.SerializedName

@Entity(tableName = FOOD_JOKE_TABLE)
class FoodJokeEntity(
    @Embedded
    var foodJoke: FoodJoke
) {
    @PrimaryKey(autoGenerate = false)
    var id: Int = 0
}