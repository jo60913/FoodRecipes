package com.example.foodrecipes.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.foodrecipes.util.Constants.Companion.FAVORITES_RECEIPE_TABLE

@Entity(tableName = FAVORITES_RECEIPE_TABLE)
class FavoritesEntity(
    @PrimaryKey(autoGenerate = true)
    var id:Int,
    val result:com.example.foodrecipes.module.Result
)