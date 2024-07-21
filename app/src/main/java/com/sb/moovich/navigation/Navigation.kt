package com.sb.moovich.navigation

import androidx.navigation.NavController
import com.sb.moovich.R
import com.sb.moovich.core.navigation.INavigation
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
}