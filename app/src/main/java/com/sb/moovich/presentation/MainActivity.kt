package com.sb.moovich.presentation

import android.annotation.SuppressLint
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.ViewTreeObserver
import android.view.WindowInsetsController
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import com.sb.moovich.R
import com.sb.moovich.core.navigation.INavigation
import com.sb.moovich.databinding.ActivityMainBinding
import com.sb.moovich.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    @Inject lateinit var navigation: INavigation
    @Inject lateinit var sharedPrefs: SharedPreferences
    private val viewModel: MainViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding
    private val navController by lazy {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.mainActivityFragmentContainer) as NavHostFragment
        navHostFragment.navController
    }

    private var isReady = false

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        observeLogin()
        enableEdgeToEdge()
        installLocalSplashScreen()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setWindowViews()
    }

    private fun observeLogin() {
        lifecycleScope.launch {
            viewModel.isLoggedIn
                .flowWithLifecycle(lifecycle, Lifecycle.State.CREATED)
                .collect { isLoggedIn ->
                    isLoggedIn?.let {
                        val startDestination =
                            if (checkFirstLogin()) R.id.onboardingFragment
                            else if (it) R.id.homeFlowFragment
                            else R.id.authorizationFragment
                        val graph =
                            navController.navInflater.inflate(R.navigation.app_navigation)
                        graph.setStartDestination(startDestination)
                        navController.graph = graph
                        navigation.setNavController(navController)
                        isReady = true
                    }
                }
        }
    }

    private fun checkFirstLogin(): Boolean {
        return sharedPrefs.getBoolean(IS_FIRST_LOGIN_KEY, true)
    }

    private fun installLocalSplashScreen() {
        val content: View = findViewById(android.R.id.content)
        content.viewTreeObserver.addOnPreDrawListener(
            object : ViewTreeObserver.OnPreDrawListener {
                @SuppressLint("ResourceType")
                override fun onPreDraw(): Boolean {
                    return if (isReady) {
                        content.viewTreeObserver.removeOnPreDrawListener(this)
                        true
                    } else {
                        false
                    }
                }
            }
        )
    }

    private fun setWindowViews() {
        window.statusBarColor = Color.TRANSPARENT
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.decorView.windowInsetsController?.setSystemBarsAppearance(
                0,
                WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
            )
        }
        setOnApplyWindowInsetsListener()
    }

    private fun setOnApplyWindowInsetsListener() {
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            val ime = insets.getInsets(WindowInsetsCompat.Type.ime()) // keyboard
            val bottom = if (ime.bottom > 0) ime.bottom else systemBars.bottom
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, bottom)
            insets
        }
    }

    companion object {
        private const val IS_FIRST_LOGIN_KEY = "is_first_login"
    }
}
