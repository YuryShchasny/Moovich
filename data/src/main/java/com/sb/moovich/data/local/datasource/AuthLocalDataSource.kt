package com.sb.moovich.data.local.datasource

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

private const val USER_PREFERENCES = "com.sb.moovich.preferences"

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(USER_PREFERENCES)

@Singleton
class AuthLocalDataSource @Inject constructor(
    @ApplicationContext context: Context
) {
    private val dataStore: DataStore<Preferences> = context.dataStore
    var token = ""
        private set

    init {
        CoroutineScope(Dispatchers.IO).launch {
            dataStore.data.collect { prefs ->
                token = prefs[TOKEN] ?: ""
            }
        }
    }

    suspend fun saveToken(token: String) {
        withContext(Dispatchers.IO) {
            dataStore.edit { preferences ->
                preferences[TOKEN] = token
            }
        }
    }

    fun setToken(token: String) {
        this.token = token
    }

    companion object {
        val TOKEN = stringPreferencesKey("api token")
    }
}
