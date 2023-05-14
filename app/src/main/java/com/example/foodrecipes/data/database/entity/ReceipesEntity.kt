package com.example.foodrecipes.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.foodrecipes.module.FoodReceipt
import com.example.foodrecipes.util.Constants.Companion.RECIPES_TABLE

@Entity(tableName = RECIPES_TABLE)
class ReceipesEntity(
    var foodReceipt: FoodReceipt
) {
    //每次API拿新的資料就會覆蓋舊的 所以不需要遞增
    @PrimaryKey(autoGenerate = false)
    var id:Int = 0
}