package com.sb.moovich.core.navigation

import androidx.navigation.NavController
import com.sb.moovich.domain.entity.Collection
import com.sb.moovich.domain.entity.GetAllType

interface INavigation {
    fun setNavController(navController: NavController)

    fun navigateUp()

    fun navigateToFilter()

    fun navigateToMovie(movieId: Int)

    fun navigateToAllMovies(type:GetAllType)

    fun navigateToAllCollections()

    fun navigateToCollection(collection: Collection)

    fun navigateToHome()

    fun navigateToAuth()
}