package com.example.foodrecipes.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.foodrecipes.core.util.Constants.Companion.FAVORITES_RECEIPE_TABLE
import com.example.foodrecipes.data.module.Result

@Entity(tableName = FAVORITES_RECEIPE_TABLE)
class FavoritesEntity(
    @PrimaryKey(autoGenerate = true)
    var id:Int,
    val result: Result
)