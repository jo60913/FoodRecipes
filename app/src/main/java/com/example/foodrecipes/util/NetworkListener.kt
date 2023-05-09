package com.example.foodrecipes.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import kotlinx.coroutines.flow.MutableStateFlow


class NetworkListener : ConnectivityManager.NetworkCallback() {
    private val isNetworkAvailable = MutableStateFlow(false)

    fun checkNetworkAvailable(context: Context):MutableStateFlow<Boolean>{
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        connectivityManager.registerDefaultNetworkCallback(this)

        var isConnected = false

        connectivityManager.allNetworks.forEach {
            val networkCapability = connectivityManager.getNetworkCapabilities(it)
            networkCapability?.let {
                //確認有沒有網路而已
                if(it.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)){
                    isConnected = true
                    return@forEach
                }
            }
        }
        isNetworkAvailable.value = isConnected
        return isNetworkAvailable
    }

    override fun onAvailable(network: Network) {
        isNetworkAvailable.value = true //有網路時就為true
    }

    override fun onLost(network: Network) {
        isNetworkAvailable.value = false    //沒有網路就為false
    }
}