package com.example.foodrecipes.presentation.bindingadapter

import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.foodrecipes.data.database.entity.FoodJokeEntity
import com.example.foodrecipes.data.module.FoodJoke
import com.example.foodrecipes.core.util.NetworkResult
import com.google.android.material.card.MaterialCardView

class FoodJokeBinding {

    companion object{

        @BindingAdapter("readApiResponse3","readDatabase3", requireAll = false)
        @JvmStatic
        fun setCareAndProgressVisibility(
            view:View,
            ApiResponse: NetworkResult<FoodJoke>?,
            database:List<FoodJokeEntity>?
        ){
            when(ApiResponse){
                is NetworkResult.Loading ->{
                    when(view){
                        is ProgressBar ->{
                            view.visibility = View.VISIBLE
                        }

                        is MaterialCardView ->{
                            view.visibility = View.INVISIBLE
                        }
                    }
                }
                is NetworkResult.Error ->{
                    when(view){
                        is ProgressBar ->{
                            view.visibility = View.INVISIBLE
                        }
                        is MaterialCardView ->{
                            view.visibility = View.VISIBLE
                            if(database != null){
                                if(database.isEmpty()){
                                    view.visibility = View.INVISIBLE
                                }
                            }
                        }
                    }
                }

                is NetworkResult.Success ->{
                    when(view) {
                        is ProgressBar ->{
                            view.visibility = View.INVISIBLE
                        }

                        is MaterialCardView ->{
                            view.visibility = View.VISIBLE
                        }
                    }
                }
            }
        }

        @BindingAdapter("readApiResponse4","readDatabase4", requireAll = false)
        @JvmStatic
        fun setErrorViewVisiblity(
            view:View,
            apiResponse: NetworkResult<FoodJoke>?,
            database:List<FoodJokeEntity>?
        ){
            if(database != null){
                if(database.isNotEmpty()){
                    view.visibility = View.VISIBLE
                    if(view is TextView){
                        if(apiResponse != null){
                            view.text = apiResponse.message.toString()
                        }
                    }
                }
            }

            if(apiResponse is NetworkResult.Success){
                view.visibility = View.INVISIBLE
            }
        }
    }
}