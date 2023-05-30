package com.example.foodrecipes.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.foodrecipes.R
import com.example.foodrecipes.module.ExtendedIngredient
import com.example.foodrecipes.util.Constants.Companion.BASE_IMAGE_URL
import com.example.foodrecipes.util.RecipesDiffUtil

class IngredientsAdapter : RecyclerView.Adapter<IngredientsAdapter.MyViewHolder>() {
    private var ingredientList = emptyList<ExtendedIngredient>()
    class MyViewHolder(itemview: View) : RecyclerView.ViewHolder(itemview)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.ingredients_row_layout,parent,false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val ingredientImageview = holder.itemView.findViewById<ImageView>(R.id.ingredient_imageView)
        val ingredientName = holder.itemView.findViewById<TextView>(R.id.ingredient_name)
        val ingredientAmout = holder.itemView.findViewById<TextView>(R.id.ingredient_amount)
        val ingredientUnit = holder.itemView.findViewById<TextView>(R.id.ingredient_unit)
        val ingredientconsistency = holder.itemView.findViewById<TextView>(R.id.ingredient_consistency)
        val ingredientOriginal = holder.itemView.findViewById<TextView>(R.id.ingredient_original)

        val item = ingredientList[position]
        ingredientImageview.load(BASE_IMAGE_URL + item.image){
            crossfade(600)
            error(R.drawable.ic_error_placeholder)
        }
        ingredientName.text = item.name
        ingredientAmout.text = item.amount.toString()
        ingredientUnit.text = item.unit
        ingredientconsistency.text = item.consistency
        ingredientOriginal.text = item.consistency
    }

    override fun getItemCount() = ingredientList.size

    fun setData(newIngredients:List<ExtendedIngredient>){
        val ingredientsDiff = RecipesDiffUtil(this.ingredientList,newIngredients)
        val diffUtilResult = DiffUtil.calculateDiff(ingredientsDiff)
        this.ingredientList = newIngredients
        diffUtilResult.dispatchUpdatesTo(this)
    }
}