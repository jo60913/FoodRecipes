package com.example.foodrecipes.presentation.ui.fragment.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import coil.load
import com.example.foodrecipes.R
import com.example.foodrecipes.databinding.FragmentOverviewBinding
import com.example.foodrecipes.data.module.Result
import com.example.foodrecipes.core.util.Constants.Companion.CLICK_RECIPE
import com.example.foodrecipes.core.util.Constants.Companion.RECIPES_BUNDLE_KEY
import com.google.firebase.analytics.FirebaseAnalytics
import org.jsoup.Jsoup


class OverviewFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = FragmentOverviewBinding.inflate(inflater,container,false)

        val args = arguments
        val myBundle : Result? = args?.getParcelable<Result>(RECIPES_BUNDLE_KEY)

        view.mainImageView.load(myBundle?.image)
        view.titleTextView.text = myBundle?.title
        view.likesTextView.text = myBundle?.aggregateLikes.toString()
        view.timeTextView.text = myBundle?.readyInMinutes.toString()
        view.summaryTextView.text = Jsoup.parse(myBundle?.summary.toString()).text()

        if(myBundle?.vegetarian == true){
            view.veganImageView.setColorFilter(ContextCompat.getColor(requireContext(), R.color.green))
            view.veganTextView.setTextColor(ContextCompat.getColor(requireContext(), R.color.green))
        }

        if(myBundle?.vegan == true){
            view.veganImageView.setColorFilter(ContextCompat.getColor(requireContext(), R.color.green))
            view.veganTextView.setTextColor(ContextCompat.getColor(requireContext(), R.color.green))
        }

        if(myBundle?.glutenFree == true){
            view.glutenFreeImageView.setColorFilter(ContextCompat.getColor(requireContext(), R.color.green))
            view.glutenFreeTextView.setTextColor(ContextCompat.getColor(requireContext(), R.color.green))
        }

        if(myBundle?.dairyFree == true){
            view.dairyFreeImageView.setColorFilter(ContextCompat.getColor(requireContext(), R.color.green))
            view.dairyFreeTextView.setTextColor(ContextCompat.getColor(requireContext(), R.color.green))
        }

        if(myBundle?.veryHealthy == true){
            view.healthyImageView.setColorFilter(ContextCompat.getColor(requireContext(), R.color.green))
            view.healthyTextView.setTextColor(ContextCompat.getColor(requireContext(), R.color.green))
        }

        if(myBundle?.cheap == true){
            view.cheapImageView.setColorFilter(ContextCompat.getColor(requireContext(), R.color.green))
            view.cheapTextView.setTextColor(ContextCompat.getColor(requireContext(), R.color.green))
        }
        val databundle = Bundle()
        databundle.putString("ClickRecipe",myBundle?.title)
        FirebaseAnalytics.getInstance(requireContext()).logEvent(CLICK_RECIPE,databundle)

        return view.root
    }


}