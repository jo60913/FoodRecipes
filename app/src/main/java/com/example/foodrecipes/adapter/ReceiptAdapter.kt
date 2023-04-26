package com.example.foodrecipes.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.foodrecipes.databinding.RecipesRowLayoutBinding
import com.example.foodrecipes.module.FoodReceipt
import com.example.foodrecipes.module.Result
import com.example.foodrecipes.util.RecipesDiffUtil

class ReceiptAdapter : RecyclerView.Adapter<ReceiptAdapter.MyViewHolder>() {
    private var recipeList = emptyList<Result>()
    class MyViewHolder(private val binding:RecipesRowLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(result : Result){
            binding.result = result
            binding.executePendingBindings()    //當result更新時layout也要跟著更新
        }

        companion object{
            fun from(parent: ViewGroup):MyViewHolder{
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = RecipesRowLayoutBinding.inflate(layoutInflater,parent,false)
                return MyViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentRecipe = recipeList[position]
        holder.bind(currentRecipe)
    }

    override fun getItemCount(): Int {
        return recipeList.size
    }

    fun setData(newData : FoodReceipt){
        val recipesDiffUtil = RecipesDiffUtil(recipeList,newData.results)
        val diffUtilResult = DiffUtil.calculateDiff(recipesDiffUtil)
        recipeList = newData.results
        diffUtilResult.dispatchUpdatesTo(this)

    }
}