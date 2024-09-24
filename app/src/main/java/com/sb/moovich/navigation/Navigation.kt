package com.sb.moovich.navigation

import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.navOptions
import com.sb.moovich.R
import com.sb.moovich.core.navigation.INavigation
import com.sb.moovich.domain.entity.Collection
import com.sb.moovich.domain.entity.GetAllType
import com.sb.moovich.presentation.all.ui.AllMoviesFragmentArgs
import com.sb.moovich.presentation.collection.ui.CollectionFragmentArgs
import com.sb.moovich.presentation.info.ui.MovieInfoFragmentArgs
import javax.inject.Inject

class Navigation @Inject constructor(): INavigation {
    private var navController: NavController? = null

    override fun setNavController(navController: NavController) {
        this.navController = navController
    }

    override fun navigateUp() {
        navController?.navigateUp()
    }

    override fun navigateToFilter() {
        navController?.navigate(R.id.toFilter)
    }

    override fun navigateToMovie(movieId: Int) {
        val args = MovieInfoFragmentArgs(movieId)
        navController?.navigate(R.id.toMovie, args.toBundle())
    }

    override fun navigateToAllMovies(type: GetAllType) {
        val args = AllMoviesFragmentArgs(type)
        navController?.navigate(R.id.toAllMovies, args.toBundle())
    }

    override fun navigateToAllCollections() {
        navController?.navigate(R.id.toAllCollections)
    }

    override fun navigateToCollection(collection: Collection) {
        val args = CollectionFragmentArgs(collection)
        navController?.navigate(R.id.toCollection, args.toBundle())
    }

    override fun navigateToHome() {
        val options = navOptions {
            popUpTo(R.id.authorizationFragment) {
                inclusive = true
            }
        }
        navController?.navigate(R.id.homeFlowFragment, options)
    }
}