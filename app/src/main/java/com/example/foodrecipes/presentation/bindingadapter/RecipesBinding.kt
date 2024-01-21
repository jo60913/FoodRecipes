package com.example.foodrecipes.presentation.bindingadapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import androidx.navigation.findNavController
import com.example.foodrecipes.data.database.entity.ReceipesEntity
import com.example.foodrecipes.data.module.FoodReceipt
import com.example.foodrecipes.presentation.ui.fragment.main.RecipesFragmentDirections

import com.example.foodrecipes.core.util.NetworkResult
import com.example.foodrecipes.data.module.Result
import com.google.firebase.crashlytics.FirebaseCrashlytics

class RecipesBinding {
    companion object {

        @BindingAdapter("onRecipeClickListener")
        @JvmStatic
        fun onRecipeClickListener(
            constraintLayout:ConstraintLayout,
            result: Result
        ){
            try{
                constraintLayout.setOnClickListener {
                    val action = RecipesFragmentDirections.actionRecipesFragmentToDetailActivity(result)
                    constraintLayout.findNavController().navigate(action)
                }
            }catch (e:java.lang.Exception){
                FirebaseCrashlytics.getInstance().recordException(e)
            }
        }

        //打上require = true代表編譯器會檢查是否兩個都有輸入
        @BindingAdapter("readApiResponse","readDatabase", requireAll = true)
        @JvmStatic
        fun errorimageViewVisibility(
            imageView: ImageView,
            apiResponse: NetworkResult<FoodReceipt>?,
            database: List<ReceipesEntity>?
        ){
            if(apiResponse is NetworkResult.Error && database.isNullOrEmpty()){
                imageView.visibility = View.VISIBLE
            }else if(apiResponse is NetworkResult.Loading){
                imageView.visibility = View.INVISIBLE
            }else if(apiResponse is NetworkResult.Success){
                imageView.visibility = View.INVISIBLE
            }
        }

        @BindingAdapter("readApiResponse2","readDatabase2", requireAll = true)
        @JvmStatic
        fun errorTextViewVisibility(
            textView: TextView,
            apiResponse: NetworkResult<FoodReceipt>?,
            database: List<ReceipesEntity>?
        ){
            if(apiResponse is NetworkResult.Error && database.isNullOrEmpty()){
                textView.visibility = View.VISIBLE
                textView.text = apiResponse.message.toString()
            }else if(apiResponse is NetworkResult.Loading){
                textView.visibility = View.INVISIBLE
            }else if(apiResponse is NetworkResult.Success){
                textView.visibility = View.INVISIBLE
            }
        }

    }
}