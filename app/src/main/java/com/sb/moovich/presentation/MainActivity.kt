package com.sb.moovich.presentation

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowInsetsController
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.sb.moovich.R
import com.sb.moovich.core.navigation.INavigation
import com.sb.moovich.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    @Inject lateinit var navigation: INavigation
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setWindowViews()
        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (getNavViewVisibility(destination)) {
                binding.navView.visibility = View.VISIBLE
                window.navigationBarColor = ContextCompat.getColor(this, com.sb.moovich.core.R.color.nav_bar)

            } else {
                binding.navView.visibility = View.GONE
                window.navigationBarColor = ContextCompat.getColor(this, com.sb.moovich.core.R.color.black)
            }
        }
        navigation.setNavController(navController)
        binding.navView.setupWithNavController(navController)
    }

    private fun setWindowViews() {
        window.statusBarColor = Color.TRANSPARENT
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.decorView.windowInsetsController?.setSystemBarsAppearance(0, WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS)
        }
        setOnApplyWindowInsetsListener()
    }

    private fun setOnApplyWindowInsetsListener() {
        ViewCompat.setOnApplyWindowInsetsListener(binding.navView) { v, insets ->
            v.setPadding(0, 0, 0, 0)
            insets
        }
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            v.setPadding(
                0,
                insets.getInsets(WindowInsetsCompat.Type.statusBars()).top,
                0,
                insets.getInsets(WindowInsetsCompat.Type.navigationBars()).bottom
            )
            insets
        }
    }

    private fun getNavViewVisibility(destination: NavDestination): Boolean =
        when (destination.id) {
            R.id.navigation_home,
            R.id.navigation_search,
            R.id.navigation_watch_list -> true

            else -> false
        }
}
