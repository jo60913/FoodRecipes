package com.example.foodrecipes.presentation.ui.bottomsheet

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.findNavController
import com.example.foodrecipes.databinding.RecipesBottomSheetBinding
import com.example.foodrecipes.core.util.Constants.Companion.DEFAULT_DIET_TYPE
import com.example.foodrecipes.core.util.Constants.Companion.DEFAULT_MEAL_TYPE
import com.example.foodrecipes.core.util.Constants.Companion.DIET_TYEP
import com.example.foodrecipes.core.util.Constants.Companion.FILTER_RECIPE
import com.example.foodrecipes.core.util.Constants.Companion.MEAL_TYPE
import com.example.foodrecipes.presentation.viewmodel.RecipesViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.google.firebase.analytics.FirebaseAnalytics
import java.util.*


class RecipesBottomSheet : BottomSheetDialogFragment() {
    private lateinit var recipesViewModel: RecipesViewModel

    private var mealTypeChip = DEFAULT_MEAL_TYPE
    private var mealTypeChipId = 0
    private var dietTypeChip = DEFAULT_DIET_TYPE
    private var dietTypeChipId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        recipesViewModel = ViewModelProvider(requireActivity()).get(RecipesViewModel::class.java)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val mView = RecipesBottomSheetBinding.inflate(inflater, container, false)

        recipesViewModel.realMealAndDietType.asLiveData().observe(viewLifecycleOwner) {
            mealTypeChip = it.selectedMealType
            dietTypeChip = it.selectedDietType

            updateChip(it.selectedMealTypeId, mView.mealTypeChipGroup)
            updateChip(it.selectedDietTypeId, mView.dietTypeChipGroup)
        }

        mView.mealTypeChipGroup.setOnCheckedChangeListener { group, selectedChipId ->
            val chip = group.findViewById<Chip>(selectedChipId)
            val selectedMealType = chip.text.toString().toLowerCase(Locale.ROOT)
            mealTypeChip = selectedMealType
            mealTypeChipId = selectedChipId
        }

        mView.dietTypeChipGroup.setOnCheckedChangeListener { group, selectedChipId ->   //按下時紀錄
            val chip = group.findViewById<Chip>(selectedChipId)
            val selectedDietType = chip.text.toString().toLowerCase(Locale.ROOT)
            dietTypeChip = selectedDietType
            dietTypeChipId = selectedChipId
        }

        mView.applyBtn.setOnClickListener {     //按下時儲存
            recipesViewModel.saveMealAndDietType(
                mealTypeChip,
                mealTypeChipId,
                dietTypeChip,
                dietTypeChipId
            )
            val filterbundle = Bundle()
            filterbundle.putString(MEAL_TYPE,mealTypeChip)
            filterbundle.putString(DIET_TYEP,dietTypeChip)
            FirebaseAnalytics.getInstance(requireContext()).logEvent(FILTER_RECIPE,filterbundle)
            val action = RecipesBottomSheetDirections.actionRecipesBottomSheetToRecipesFragment(true)    //執行在navigation的action這個action可以在navigation的xml中找到。RecipesBottomSheetDirections是navigation自動產生的
            findNavController().navigate(action)
        }

        return mView.root
    }

    /**
     * 依照該傳進來的ID使該chip呈現選中的狀態
     */
    private fun updateChip(chipID: Int, chipGroup: ChipGroup) {
        if (chipID != 0) {
            try {
                chipGroup.findViewById<Chip>(chipID).isChecked = true
            } catch (e: Exception) {

            }
        }
    }

}