package com.example.foodrecipes.presentation.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.navArgs
import com.example.foodrecipes.R
import com.example.foodrecipes.presentation.adapter.PageAdapter
import com.example.foodrecipes.data.database.entity.FavoritesEntity
import com.example.foodrecipes.databinding.ActivityDetailBinding
import com.example.foodrecipes.presentation.ui.fragment.detail.IngredientsFragment
import com.example.foodrecipes.presentation.ui.fragment.detail.InstructionsFragment
import com.example.foodrecipes.presentation.ui.fragment.detail.OverviewFragment
import com.example.foodrecipes.core.util.Constants.Companion.INGREDIENTS
import com.example.foodrecipes.core.util.Constants.Companion.INSTRUCTIONS
import com.example.foodrecipes.core.util.Constants.Companion.OVERVIEW
import com.example.foodrecipes.core.util.Constants.Companion.RECIPES_BUNDLE_KEY
import com.example.foodrecipes.presentation.viewmodel.MainViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {
    private val args by navArgs<DetailActivityArgs>()
    private val mainviewmodel: MainViewModel by viewModels()
    private lateinit var binding:ActivityDetailBinding

    private var recipeSave = false
    private var saveRecipeId = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        binding = DataBindingUtil.setContentView(this,R.layout.activity_detail,)
        setSupportActionBar(binding.toolbar)
        binding.toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.white))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val fragments = ArrayList<Fragment>()
        fragments.add(OverviewFragment())
        fragments.add(IngredientsFragment())
        fragments.add(InstructionsFragment())

        val titles = ArrayList<String>()
        titles.add(OVERVIEW)
        titles.add(INGREDIENTS)
        titles.add(INSTRUCTIONS)

        val resultBundle = Bundle()
        resultBundle.putParcelable(RECIPES_BUNDLE_KEY,args.result)

        val adapter = PageAdapter(
            resultBundle,
            fragments,
            titles,
            supportFragmentManager
        )
        binding.viewpager.adapter = adapter
        binding.tableLayout.setupWithViewPager(binding.viewpager)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.detail_menu,menu)
        val menuItem = menu?.findItem(R.id.save_tofavorite_menu)
        checkSaveRecipes(menuItem!!)
        return true
    }

    private fun checkSaveRecipes(menuItem: MenuItem) {
        mainviewmodel.readFavoriteRecipes.observe(this){
            try{
                for(saveRecipe in it){
                    if(saveRecipe.result.id == args.result.id){
                        changeMenuItemColor(menuItem,R.color.yellow)
                        saveRecipeId = saveRecipe.id
                        recipeSave = true
                        break
                    }else{
                        changeMenuItemColor(menuItem,R.color.white)
                    }
                }
            }catch (e:java.lang.Exception){
                Log.e("detailActivity","找尋我的最愛食譜時錯誤 ${e.message}")
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == android.R.id.home) {
            finish()
        }else if (item.itemId == R.id.save_tofavorite_menu && !recipeSave){
            saveToFavorite(item)
        }else if (item.itemId == R.id.save_tofavorite_menu && recipeSave){
            removeFromFavorite(item)
        }
        return super.onOptionsItemSelected(item)
    }

    private fun saveToFavorite(item: MenuItem) {
        val favoritesEntity = FavoritesEntity(0,args.result)
        mainviewmodel.insertFavoriteRecipes(favoritesEntity)
        changeMenuItemColor(item,R.color.yellow)
        showSnackbar("食譜儲存")
        recipeSave = true
    }

    private fun removeFromFavorite(item:MenuItem){
        val favoritesEntity = FavoritesEntity(
            saveRecipeId,
            args.result
        )
        mainviewmodel.deleteFacoriteRecipes(favoritesEntity)
        changeMenuItemColor(item,R.color.white)
        showSnackbar("我的最愛已刪除")
        recipeSave = false
    }

    private fun showSnackbar(content: String) {
        Snackbar.make(
            binding.root,
            content,
            Snackbar.LENGTH_LONG
        ).setAction("Ok"){}
            .show()
    }

    private fun changeMenuItemColor(item: MenuItem, color: Int) {
        item.icon.setTint(ContextCompat.getColor(this,color))
    }
}