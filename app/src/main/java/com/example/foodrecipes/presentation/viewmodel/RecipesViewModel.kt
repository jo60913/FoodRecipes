package com.example.foodrecipes.presentation.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.foodrecipes.data.DataStoreRepositoryImpl
import com.example.foodrecipes.core.util.Constants.Companion.API_KEY
import com.example.foodrecipes.core.util.Constants.Companion.DEFAULT_DIET_TYPE
import com.example.foodrecipes.core.util.Constants.Companion.DEFAULT_MEAL_TYPE
import com.example.foodrecipes.core.util.Constants.Companion.DEFAULT_RECIPES_NUMBER
import com.example.foodrecipes.core.util.Constants.Companion.QUERY_ADD_RECIPE_INFORMATION
import com.example.foodrecipes.core.util.Constants.Companion.QUERY_API_KEY
import com.example.foodrecipes.core.util.Constants.Companion.QUERY_DIET
import com.example.foodrecipes.core.util.Constants.Companion.QUERY_FILL_INGREDIENTS
import com.example.foodrecipes.core.util.Constants.Companion.QUERY_NUMBER
import com.example.foodrecipes.core.util.Constants.Companion.QUERY_SEARCH
import com.example.foodrecipes.core.util.Constants.Companion.QUERY_TYPE
import com.example.foodrecipes.domain.MealAndDietTypeSaveUseCase
import com.example.foodrecipes.domain.SaveBackOnlineUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipesViewModel @Inject constructor(
    application: Application,
    dataStoreRepository: DataStoreRepositoryImpl,
    private val mealAndDietTypeSaveUseCase: MealAndDietTypeSaveUseCase,
    private val saveBackOnlineUseCase: SaveBackOnlineUseCase
) : AndroidViewModel(application) {

    private var mealType = DEFAULT_MEAL_TYPE
    private var dietType = DEFAULT_DIET_TYPE

    val realMealAndDietType = dataStoreRepository.realMealAndDietType
    val readBackOnline = dataStoreRepository.realBackOnline.asLiveData()
    var networkStatus = false
    var backOnline = false

    fun saveMealAndDietType(
        mealType: String,
        mealTypeId: Int,
        dietType: String,
        dietTypeId: Int
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            mealAndDietTypeSaveUseCase.execute(
                mealType,
                mealTypeId,
                dietType,
                dietTypeId
            )
        }
    }

    private fun saveBackOnline(backOnline:Boolean) =
        viewModelScope.launch(Dispatchers.IO) {
            saveBackOnlineUseCase.execute(backOnline)
        }

    fun allyQuery(): HashMap<String, String> {
        viewModelScope.launch {
            realMealAndDietType.collect{
                mealType = it.selectedDietType
                dietType = it.selectedDietType
            }
        }
        val query: HashMap<String, String> = HashMap()
        query[QUERY_NUMBER] = DEFAULT_RECIPES_NUMBER
        query[QUERY_API_KEY] = API_KEY
        query[QUERY_TYPE] = mealType
        query[QUERY_DIET] = dietType
        query[QUERY_ADD_RECIPE_INFORMATION] = "true"
        query[QUERY_FILL_INGREDIENTS] = "true"
        return query
    }

    fun applySearchQuery(searchQuery:String) : HashMap<String,String>{
        val queries:HashMap<String,String> = HashMap()
        queries[QUERY_SEARCH] = searchQuery
        queries[QUERY_NUMBER] = DEFAULT_RECIPES_NUMBER
        queries[QUERY_API_KEY] = API_KEY
        queries[QUERY_ADD_RECIPE_INFORMATION] = "true"
        queries[QUERY_FILL_INGREDIENTS] = "true"
        return queries
    }

    fun showNetworkStatus(){
        if(!networkStatus) {
            Toast.makeText(getApplication(), "沒有網路", Toast.LENGTH_LONG).show()
            saveBackOnline(true)
        }else{
            if(backOnline) {
                Toast.makeText(getApplication(), "網路正常", Toast.LENGTH_LONG).show()
                saveBackOnline(false)
            }
        }
    }

}