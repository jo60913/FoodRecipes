package com.example.foodrecipes.bindingadapter

import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import androidx.navigation.findNavController
import com.example.foodrecipes.data.database.ReceipesEntity
import com.example.foodrecipes.data.database.RecipeDatabase
import com.example.foodrecipes.module.FoodReceipt
import com.example.foodrecipes.ui.fragment.main.RecipesFragmentDirections

import com.example.foodrecipes.util.NetworkResult

class RecipesBinding {
    companion object {

        @BindingAdapter("onRecipeClickListener")
        @JvmStatic
        fun onRecipeClickListener(
            constraintLayout:ConstraintLayout,
            result:com.example.foodrecipes.module.Result
        ){
            try{
                constraintLayout.setOnClickListener {
                    val action = RecipesFragmentDirections.actionRecipesFragmentToDetailActivity(result)
                    constraintLayout.findNavController().navigate(action)
                }
            }catch (e:java.lang.Exception){
                Log.e("錯誤","點下食譜時錯誤")
            }
        }

        //打上require = true代表編譯器會檢查是否兩個都有輸入
        @BindingAdapter("readApiResponse","readDatabase", requireAll = true)
        @JvmStatic
        fun errorimageViewVisibility(
            imageView: ImageView,
            apiResponse:NetworkResult<FoodReceipt>?,
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