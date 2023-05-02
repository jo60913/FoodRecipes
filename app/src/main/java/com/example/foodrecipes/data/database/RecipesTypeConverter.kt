package com.example.foodrecipes.data.database

import androidx.room.TypeConverter
import com.example.foodrecipes.module.FoodReceipt
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class RecipesTypeConverter {

    var gson = Gson()

    @TypeConverter
    fun foodRecipesToString(foodReceipt: FoodReceipt):String{
        return gson.toJson(foodReceipt)
    }

    @TypeConverter
    fun stringToFoodRecipe(data:String):FoodReceipt{
        val listType = object : TypeToken<FoodReceipt>() {}.type
        return gson.fromJson(data,listType)
    }
}