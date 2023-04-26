package com.example.foodrecipes.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.foodrecipes.util.Constants
import com.example.foodrecipes.util.Constants.Companion.API_KEY
import com.example.foodrecipes.util.Constants.Companion.QUERY_ADD_RECIPE_INFORMATION
import com.example.foodrecipes.util.Constants.Companion.QUERY_API_KEY
import com.example.foodrecipes.util.Constants.Companion.QUERY_DIET
import com.example.foodrecipes.util.Constants.Companion.QUERY_FILL_INGREDIENTS
import com.example.foodrecipes.util.Constants.Companion.QUERY_NUMBER
import com.example.foodrecipes.util.Constants.Companion.QUERY_TYPE

class RecipesViewModel(application: Application) :AndroidViewModel(application){
    fun allyQuery():HashMap<String,String>{
        val query:HashMap<String,String> = HashMap()
        query[QUERY_NUMBER] = "50"
        query[QUERY_API_KEY] = API_KEY
        query[QUERY_TYPE] = "snack"
        query[QUERY_DIET] = "vegan"
        query[QUERY_ADD_RECIPE_INFORMATION] = "true"
        query[QUERY_FILL_INGREDIENTS] = "true"
        return query
    }
}