package com.example.foodrecipes.util
import androidx.recyclerview.widget.DiffUtil
import com.example.foodrecipes.module.Result
import kotlin.math.sign

class RecipesDiffUtil(
    private val oldList : List<Result>,
    private val newList : List<Result>

) : DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    //確認兩的對象是否相同
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[newItemPosition] === newList[newItemPosition]
    }

    //只有在areItemTheSame為true時，確認內容是否相同
    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }

}