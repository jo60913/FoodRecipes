package com.example.foodrecipes.core.util

class Constants {
    companion object{
        const val API_KEY = "b766b87fef06435cbfa60aa0c80f149d"
        const val BASE_URL = "https://api.spoonacular.com"
        const val BASE_IMAGE_URL = "https://spoonacular.com/cdn/ingredients_100x100/"
        //搜尋
        const val QUERY_SEARCH = "query"
        const val QUERY_NUMBER = "number"
        const val QUERY_API_KEY = "apiKey"
        const val QUERY_TYPE = "type"
        const val QUERY_DIET = "diet"
        const val QUERY_ADD_RECIPE_INFORMATION = "addRecipeInformation"
        const val QUERY_FILL_INGREDIENTS = "fillIngredients"

        //Room
        const val DATABASE_NAME = "recipes_database"
        const val RECIPES_TABLE = "recipes_table"
        const val FAVORITES_RECEIPE_TABLE = "favorite_recipes_table"
        //Button Sheet
        const val DEFAULT_RECIPES_NUMBER = "50"
        const val DEFAULT_MEAL_TYPE = "main course"
        const val DEFAULT_DIET_TYPE = "gluten free"

        const val PREFERENCES_NAME = "foody_preferences"
        const val PREFERENCES_MEAL_TYPE = "mealType"
        const val PREFERENCES_MEAL_TYPE_ID = "mealTypeID"
        const val PREFERENCES_DIET_TYPE = "dietType"
        const val PREFERENCES_DIET_TYPE_ID = "dietTypeId"
        const val PREFERENCES_BACK_ONLINE = "backonline"

        const val OVERVIEW = "介紹"
        const val INGREDIENTS = "材料"
        const val INSTRUCTIONS = "作法"
        const val RECIPES_BUNDLE_KEY = "recipesBundle"
        const val FOOD_JOKE_TABLE = "food_joke_table"

        //firebase
        const val FIREBASE_NOTIFICATION_CHANNEL_ID = "1"
        const val FIREBASE_NOTIFICATION_ID = 1
        const val LOGIN = "Login"
        const val LOGIN_ACCOUNT = "LoginAccount"
        const val CLICK_RECIPE = "ClickRecipe"
        const val SEARCH_RECIPE_FROM_SEARCHBAR = "SearchRecipeFromSearchBar"
        const val SEARCH_KEYWORD_FROM_DATABASE = "SearchKeyWordFromDatabase"
        const val FILTER_RECIPE = "FilterRecipe"
        const val MEAL_TYPE = "MealType"
        const val DIET_TYEP = "DietType"
    }
}