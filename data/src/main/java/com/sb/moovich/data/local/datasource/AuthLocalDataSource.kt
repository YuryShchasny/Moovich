package com.sb.moovich.data.local.datasource

import android.content.SharedPreferences
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthLocalDataSource @Inject constructor(
   private val sharedPreferences: SharedPreferences
) {
    var token = ""
        private set

    init {
        token = sharedPreferences.getString(TOKEN, "") ?: ""
    }

    fun saveToken(token: String) {
        sharedPreferences.edit().putString(TOKEN, token).apply()
    }

    fun setToken(token: String) {
        this.token = token
    }

    companion object {
        const val TOKEN = "api token"
    }
}
