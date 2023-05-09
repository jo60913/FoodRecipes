package com.example.foodrecipes.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodrecipes.data.DataStoreRepository
import com.example.foodrecipes.util.Constants
import com.example.foodrecipes.util.Constants.Companion.API_KEY
import com.example.foodrecipes.util.Constants.Companion.DEFAULT_DIET_TYPE
import com.example.foodrecipes.util.Constants.Companion.DEFAULT_MEAL_TYPE
import com.example.foodrecipes.util.Constants.Companion.DEFAULT_RECIPES_NUMBER
import com.example.foodrecipes.util.Constants.Companion.QUERY_ADD_RECIPE_INFORMATION
import com.example.foodrecipes.util.Constants.Companion.QUERY_API_KEY
import com.example.foodrecipes.util.Constants.Companion.QUERY_DIET
import com.example.foodrecipes.util.Constants.Companion.QUERY_FILL_INGREDIENTS
import com.example.foodrecipes.util.Constants.Companion.QUERY_NUMBER
import com.example.foodrecipes.util.Constants.Companion.QUERY_TYPE
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipesViewModel @Inject constructor(
    application: Application,
    private val dataStoreRepository: DataStoreRepository
) : AndroidViewModel(application) {

    private var mealtype = DEFAULT_MEAL_TYPE
    private var dietType = DEFAULT_DIET_TYPE

    val realMealAndDietType = dataStoreRepository.realMealAndDietType

    fun saveMealAndDietType(
        mealType: String,
        mealTypeId: Int,
        dietType: String,
        dietTypeId: Int
    ){
        viewModelScope.launch(Dispatchers.IO) {
            dataStoreRepository.saveMealAndDietType(
                mealType,
                mealTypeId,
                dietType,
                dietTypeId
            )
        }

    }

    fun allyQuery(): HashMap<String, String> {

        viewModelScope.launch {
            realMealAndDietType.collect{
                mealtype = it.selectedDietType
                dietType = it.selectedDietType
            }
        }

        val query: HashMap<String, String> = HashMap()
        query[QUERY_NUMBER] = DEFAULT_RECIPES_NUMBER
        query[QUERY_API_KEY] = API_KEY
        query[QUERY_TYPE] = mealtype
        query[QUERY_DIET] = dietType
        query[QUERY_ADD_RECIPE_INFORMATION] = "true"
        query[QUERY_FILL_INGREDIENTS] = "true"
        return query
    }
}