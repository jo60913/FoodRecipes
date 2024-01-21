package com.example.foodrecipes.navigationtest

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.example.foodrecipes.presentation.ui.MainActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

import com.example.foodrecipes.R
import com.example.foodrecipes.presentation.ui.fragment.main.RecipesFragment
import org.junit.Before

@MediumTest
@RunWith(AndroidJUnit4::class)
class NavigationTest {
    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    private lateinit var navController: NavController
    private lateinit var activityScenario : ActivityScenarioRule<MainActivity>
    @Before
    fun setup(){
        var navigationtestavHostFragmentagment = NavHostFragment.create(R.navigation.my_nav)
//        navController = mock(NavController::class.java)
//        navHostFragment.navController = navController
//
//        activityScenario = ActivityScenarioRule(MainActivity::class.java)

    }

    @Test
    fun `click_recipesFragmentOption_to_recipesFragment`(){
//        val mockNavController = mock(NavController::class.java)
        val recipesFragment = launchFragmentInContainer<RecipesFragment>()
        onView(withId(R.id.recipesFragment)).check(matches(isDisplayed()))
//        recipesFragment.onFragment{fragment ->
//            Navigation.setViewNavController(fragment.requireView(),mockNavController)
//        }
//
//        onView(withId(R.id.recipesFragmentOption)).perform(click())
//        verify(navController).navigate(R.id.action_recipesFragment_to_favoriteRecipesFragment)
    }

    @Test
    fun `click_favoriteRecipesFragmentOption_to_favoriteRecipesFragment`(){
        onView(withId(R.id.favoriteRecipesFragmentOption)).perform(click())
        onView(withId(R.id.favoriteRecipesFragment)).check(matches(isDisplayed()))
    }

    @Test
    fun `click_foodjokeFragmentOption_to_foodjokeFragment`(){
        onView(withId(R.id.foodjokeFragmentOption)).perform(click())
        onView(withId(R.id.foodjokeFragment)).check(matches(isDisplayed()))
    }
}