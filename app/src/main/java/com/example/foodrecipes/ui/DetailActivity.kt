package com.example.foodrecipes.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.navArgs
import com.example.foodrecipes.R
import com.example.foodrecipes.adapter.PageAdapter
import com.example.foodrecipes.databinding.ActivityDetailBinding
import com.example.foodrecipes.ui.fragment.detail.IngredientsFragment
import com.example.foodrecipes.ui.fragment.detail.InstructionsFragment
import com.example.foodrecipes.ui.fragment.detail.OverviewFragment
import com.example.foodrecipes.util.Constants.Companion.INGREDIENTS
import com.example.foodrecipes.util.Constants.Companion.INSTRUCTIONS
import com.example.foodrecipes.util.Constants.Companion.OVERVIEW

class DetailActivity : AppCompatActivity() {
    private val args by navArgs<DetailActivityArgs>()

    private lateinit var binding:ActivityDetailBinding
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
        resultBundle.putParcelable("recipesBundle",args.result)

        val adapter = PageAdapter(
            resultBundle,
            fragments,
            titles,
            supportFragmentManager
        )
        binding.viewpager.adapter = adapter
        binding.tableLayout.setupWithViewPager(binding.viewpager)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }
}