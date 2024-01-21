package com.example.foodrecipes.presentation.adapter

import android.view.*
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.foodrecipes.R
import com.example.foodrecipes.data.database.entity.FavoritesEntity
import com.example.foodrecipes.databinding.FavoriteRecipesRowLayoutBinding
import com.example.foodrecipes.presentation.ui.fragment.main.FavoriteRecipesFragmentDirections
import com.example.foodrecipes.core.util.RecipesDiffUtil
import com.example.foodrecipes.presentation.viewmodel.MainViewModel
import com.google.android.material.snackbar.Snackbar

class FavoriteRecipesAdapter(
    private val requireActivity: FragmentActivity,
    private val mainViewModel: MainViewModel
) : RecyclerView.Adapter<FavoriteRecipesAdapter.MyViewHolder>(), ActionMode.Callback {
    private var favoriteRecipesList = emptyList<FavoritesEntity>()
    private var multiSelection = false  //判斷是否為多選狀態
    private var myViewHolder = arrayListOf<MyViewHolder>()
    private var selectedRecipes = arrayListOf<FavoritesEntity>()
    private lateinit var actionMode: ActionMode
    private lateinit var mView:View
    class MyViewHolder(private val binding: FavoriteRecipesRowLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val layout = binding.favoriteRecipesRowLayout
        val careview = binding.favoriteRowCardView
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
        mView = holder.itemView.rootView
        myViewHolder.add(holder)
        var recipes = favoriteRecipesList[position]
        holder.bind(recipes)
        holder.itemView.setOnClickListener {
            if (multiSelection)
                applySelection(holder, recipes)
            else {
                val action =
                    FavoriteRecipesFragmentDirections.actionFavoriteRecipesFragmentToDetailActivity(
                        recipes.result
                    )
                it.findNavController().navigate(action)
            }
        }

        holder.itemView.setOnLongClickListener {    //長按時觸發
            if (!multiSelection) {
                multiSelection = true
                requireActivity.startActionMode(this)
                applySelection(holder, recipes)
                true
            } else {
                multiSelection = false
                false
            }

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
        actionMode = mode!!
        return true
    }

    override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?) = true

    override fun onActionItemClicked(mode: ActionMode?, item: MenuItem?):Boolean{
        if(item?.itemId == R.id.delete_favorite_recipe_menu){
            selectedRecipes.forEach {
                mainViewModel.deleteFavoriteRecipes(it)
            }
            showSnackbar("刪除${selectedRecipes.size}個食譜")
            multiSelection = false
            selectedRecipes.clear()
            actionMode.finish()
        }
        return true
    }

    override fun onDestroyActionMode(mode: ActionMode?) {
        multiSelection = false
        selectedRecipes.clear()
        myViewHolder.forEach {
            changeRecipesStyle(it, R.color.cardBackgroundColor, R.color.strokeColor)
        }
        applyStatueBarColor(R.color.statusBarColor)
    }

    /***
     * 改變狀態欄的顏色
     */
    private fun applyStatueBarColor(color: Int) {
        requireActivity.window.statusBarColor =
            ContextCompat.getColor(requireActivity, color)
    }

    private fun applySelection(holder: MyViewHolder, currentRecipes: FavoritesEntity) {
        if (selectedRecipes.contains(currentRecipes)) {
            selectedRecipes.remove(currentRecipes)
            applyActionModeTitle()
            changeRecipesStyle(holder, R.color.cardBackgroundColor, R.color.strokeColor)
        } else {
            selectedRecipes.add(currentRecipes)
            applyActionModeTitle()
            changeRecipesStyle(holder, R.color.cardBackgroundLightColor, R.color.colorPrimary)
        }
    }

    private fun changeRecipesStyle(
        holder: MyViewHolder,
        backgroundColor: Int,
        strokeColor: Int
    ) {
        holder.careview.strokeColor = ContextCompat.getColor(requireActivity, strokeColor)
    }

    private fun applyActionModeTitle() {
        when (selectedRecipes.size) {
            0 -> {            //如果當前沒有選擇的話就結束選擇模式
                actionMode.finish()
            }
            else -> {
                actionMode.title = "${selectedRecipes.size}個已選擇"
            }
        }
    }

    private fun showSnackbar(message:String){
        Snackbar.make(
            mView,
            message,
            Snackbar.LENGTH_SHORT
        ).setAction("OK"){}
            .show()
    }

    fun clearContentualActionMode(){
        if(this::actionMode.isInitialized)
            actionMode.finish()
    }
}