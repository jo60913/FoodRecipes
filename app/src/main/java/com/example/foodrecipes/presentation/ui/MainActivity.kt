package com.example.foodrecipes.presentation.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.foodrecipes.R
import com.example.foodrecipes.databinding.ActivityMainBinding
import com.example.foodrecipes.core.util.Constants.Companion.LOGIN
import com.example.foodrecipes.core.util.Constants.Companion.LOGIN_ACCOUNT
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.messaging.FirebaseMessaging
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var databinding : ActivityMainBinding
    private lateinit var navController : NavController
    private lateinit var firebaseAnalytics: FirebaseAnalytics
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.SplashScreenStyle)
        databinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        navController = findNavController(R.id.navHostFragment)
        firebaseAnalytics = FirebaseAnalytics.getInstance(this)
        FirebaseMessaging.getInstance().subscribeToTopic("News").addOnCompleteListener {
            if(it.isSuccessful)
                Toast.makeText(this,"成功",Toast.LENGTH_SHORT).show()
        }
        val param = Bundle()
        param.putString(LOGIN_ACCOUNT,"jo60913")
        FirebaseAnalytics.getInstance(this).logEvent(LOGIN,param)
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.recipesFragment,
                R.id.favoriteRecipesFragment,
                R.id.foodjokeFragment
            )
        )
        setSupportActionBar(databinding.toolbar)
        databinding.bottomNavigationView.setupWithNavController(navController)
        setupActionBarWithNavController(navController,appBarConfiguration)

    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() ||  super.onSupportNavigateUp()
    }
}