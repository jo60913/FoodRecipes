package com.example.foodrecipes.adapter

import android.util.Log
import android.view.*
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.foodrecipes.R
import com.example.foodrecipes.data.database.entity.FavoritesEntity
import com.example.foodrecipes.databinding.FavoriteRecipesRowLayoutBinding
import com.example.foodrecipes.ui.fragment.main.FavoriteRecipesFragmentDirections
import com.example.foodrecipes.util.RecipesDiffUtil

class FavoriteRecipesAdapter(
    private val requireActivity: FragmentActivity
) : RecyclerView.Adapter<FavoriteRecipesAdapter.MyViewHolder>(), ActionMode.Callback {
    private var favoriteRecipesList = emptyList<FavoritesEntity>()

    class MyViewHolder(private val binding: FavoriteRecipesRowLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(favoritesEntity: FavoritesEntity) {
            binding.favoritesEntity = favoritesEntity
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): MyViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = FavoriteRecipesRowLayoutBinding.inflate(layoutInflater, parent, false)
                return MyViewHolder(binding)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        var recipes = favoriteRecipesList[position]
        holder.bind(recipes)
        holder.itemView.setOnClickListener {
            val action =
                FavoriteRecipesFragmentDirections.actionFavoriteRecipesFragmentToDetailActivity(
                    recipes.result
                )
            it.findNavController().navigate(action)
        }

        holder.itemView.setOnLongClickListener {    //長按時觸發
            requireActivity.startActionMode(this)
            true
        }
    }

    override fun getItemCount() = favoriteRecipesList.size

    fun setData(newFavoriteRecipes: List<FavoritesEntity>) {
        val favoriteRecipesDiffUtil =
            RecipesDiffUtil(this.favoriteRecipesList, newFavoriteRecipes)
        val diffUtilResult = DiffUtil.calculateDiff(favoriteRecipesDiffUtil)
        this.favoriteRecipesList = newFavoriteRecipes
        diffUtilResult.dispatchUpdatesTo(this)
    }

    override fun onCreateActionMode(mode: ActionMode?, menu: Menu?): Boolean {
        mode?.menuInflater?.inflate(R.menu.favorites_contextual_menu, menu)
        applyStatueBarColor(R.color.contextStatusBarColor)
        return true
    }

    override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?) = true

    override fun onActionItemClicked(mode: ActionMode?, item: MenuItem?) = true

    override fun onDestroyActionMode(mode: ActionMode?) {
        applyStatueBarColor(R.color.statusBarColor)
    }

    /***
     * 改變狀態欄的顏色
     */
    private fun applyStatueBarColor(color: Int) {
        requireActivity.window.statusBarColor =
            ContextCompat.getColor(requireActivity,color)
    }
}