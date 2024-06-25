package com.sb.moovich.core.base

import android.net.Uri
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.NavDeepLinkRequest

class DeepLinkRequestBuilder(private val navController: NavController, deepLinkId: Int) {
    private var arguments: MutableList<Pair<Int, Any>> = mutableListOf()
    private var deepLink = ContextCompat.getString(navController.context, deepLinkId)
    private var navigateAfterBuild = false

    fun addArguments(vararg arguments: Pair<Int, Any>): DeepLinkRequestBuilder {
        arguments.forEachIndexed { index, pair ->
            deepLink = if (index == 0) {
                "$deepLink?${pair.first}={${pair.first}}"
            }
            else "$deepLink&${pair.first}={${pair.first}}"
            this.arguments.add(pair)
        }
        return this
    }

    fun setNavigateAfterBuild(navigate: Boolean): DeepLinkRequestBuilder {
        navigateAfterBuild = navigate
        return this
    }

    fun build(): NavDeepLinkRequest {
        var uri = Uri.parse(deepLink)
            .buildUpon()
        arguments.forEach {
            val argumentAsString = ContextCompat.getString(navController.context, it.first)
            uri = uri.appendQueryParameter(argumentAsString, it.second.toString())
        }
        val request = NavDeepLinkRequest.Builder
            .fromUri(uri.build())
            .build()
        if(navigateAfterBuild) navController.navigate(request)
        return request
    }
}