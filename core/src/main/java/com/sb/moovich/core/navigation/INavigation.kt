package com.sb.moovich.core.navigation

import androidx.navigation.NavController

interface INavigation {
    fun setNavController(navController: NavController)

    fun navigateUp()

    fun navigateToFilter()

    fun navigateToMovie(movieId: Int)
}