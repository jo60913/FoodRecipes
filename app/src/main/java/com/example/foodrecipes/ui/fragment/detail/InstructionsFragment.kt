package com.example.foodrecipes.ui.fragment.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import com.example.foodrecipes.databinding.FragmentInstructionsBinding
import com.example.foodrecipes.module.Result
import com.example.foodrecipes.util.Constants


class InstructionsFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentInstructionsBinding.inflate(inflater,container,false)
        val args = arguments
        val myBundle : Result? = args?.getParcelable<Result>(Constants.RECIPES_BUNDLE_KEY)

        binding.webview.webViewClient = object : WebViewClient() {}
        val website:String = if(myBundle!!.sourceUrl != null){
            myBundle!!.sourceUrl!!
        }else{
            ""
        }

        binding.webview.loadUrl(website)

        return binding.root
    }

}