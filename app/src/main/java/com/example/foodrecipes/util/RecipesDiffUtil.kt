package com.example.foodrecipes.util
import androidx.recyclerview.widget.DiffUtil

class RecipesDiffUtil<T>(
    private val oldList : List<T>,
    private val newList : List<T>

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