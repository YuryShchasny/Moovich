package com.sb.moovich.presentation.onboarding.viewmodel

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import com.sb.moovich.core.navigation.INavigation
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class OnboardingViewModel @Inject constructor(
    private val navigation: INavigation,
    private val sharedPreferences: SharedPreferences
) : ViewModel() {
    fun finishOnboarding() {
        sharedPreferences.edit().putBoolean(IS_FIRST_LOGIN_KEY, false).apply()
        navigation.navigateToAuth()
    }

    companion object {
        private const val IS_FIRST_LOGIN_KEY = "is_first_login"
    }
}
