package com.example.foodrecipes.data

import android.content.Context
import androidx.datastore.DataStore
import androidx.datastore.preferences.*
import com.example.foodrecipes.util.Constants.Companion.DEFAULT_DIET_TYPE
import com.example.foodrecipes.util.Constants.Companion.DEFAULT_MEAL_TYPE
import com.example.foodrecipes.util.Constants.Companion.PREFERENCES_BACK_ONLINE
import com.example.foodrecipes.util.Constants.Companion.PREFERENCES_DIET_TYPE
import com.example.foodrecipes.util.Constants.Companion.PREFERENCES_DIET_TYPE_ID
import com.example.foodrecipes.util.Constants.Companion.PREFERENCES_MEAL_TYPE
import com.example.foodrecipes.util.Constants.Companion.PREFERENCES_MEAL_TYPE_ID
import com.example.foodrecipes.util.Constants.Companion.PREFERENCES_NAME
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import okio.IOException
import javax.inject.Inject

@ActivityRetainedScoped
class DataStoreRepository @Inject constructor(
    @ApplicationContext private val context: Context
) {


    /**
     * 使用DataStore儲存 DataStore使用後台執行緒執行 不像sharepreference
     */
    private object PreferenceKey {
        val selectMealType = preferencesKey<String>(PREFERENCES_MEAL_TYPE)
        val selectMealTypeId = preferencesKey<Int>(PREFERENCES_MEAL_TYPE_ID)
        val selectDietType = preferencesKey<String>(PREFERENCES_DIET_TYPE)
        val selectDietTypeId = preferencesKey<Int>(PREFERENCES_DIET_TYPE_ID)
        val backOnline = preferencesKey<Boolean>(PREFERENCES_BACK_ONLINE)
    }

    private val dataStore: DataStore<Preferences> = context.createDataStore(
        name = PREFERENCES_NAME
    )

    suspend fun saveMealAndDietType(
        mealType:String,
        mealTypeId:Int,
        dietType:String,
        dietTypeId:Int
    ){
        dataStore.edit { preference ->
            preference[PreferenceKey.selectMealType] = mealType
            preference[PreferenceKey.selectMealTypeId] = mealTypeId
            preference[PreferenceKey.selectDietType] = dietType
            preference[PreferenceKey.selectDietTypeId] = dietTypeId

        }
    }

    suspend fun saveBackOnline(backonline:Boolean){
        dataStore.edit {
            it[PreferenceKey.backOnline] = backonline
        }
    }

    val realMealAndDietType : Flow<MealAndDietType> = dataStore.data
        .catch { exception ->
            if(exception is IOException)
                emit(emptyPreferences())
            else
                throw exception
        }.map { preferences ->
            val selectedMealType = preferences[PreferenceKey.selectMealType] ?: DEFAULT_MEAL_TYPE   //沒有selectMeaType就找main course
            val selectedMealTypeId = preferences[PreferenceKey.selectMealTypeId] ?: 0
            val selectedDietType = preferences[PreferenceKey.selectDietType] ?: DEFAULT_DIET_TYPE
            val selectedDietTypeId = preferences[PreferenceKey.selectDietTypeId] ?: 0
            MealAndDietType(
                selectedMealType,
                selectedMealTypeId,
                selectedDietType,
                selectedDietTypeId
            )
        }
    val realBackOnline : Flow<Boolean> = dataStore.data
        .catch { exception ->
            if(exception is IOException)
                emit(emptyPreferences())
            else
                throw exception
        }.map {
            val backonline = it[PreferenceKey.backOnline] ?: false
            backonline
        }
}

data class MealAndDietType(
    val selectedMealType: String,
    val selectedMealTypeId: Int,
    val selectedDietType: String,
    val selectedDietTypeId: Int
)