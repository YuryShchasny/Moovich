package com.sb.moovich.data.local.datasource

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

private const val USER_PREFERENCES = "com.sb.moovich.preferences"

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(USER_PREFERENCES)

class AuthLocalDataSource @Inject constructor(
    @ApplicationContext context: Context
) {
    private val dataStore: DataStore<Preferences> = context.dataStore

    suspend fun setToken(token: String) {
        withContext(Dispatchers.IO) {
            dataStore.edit { preferences ->
                preferences[TOKEN] = token
            }
        }
    }

    fun subscribeToken() = dataStore.data.map { it[TOKEN] ?: "" }

    companion object {
        val TOKEN = stringPreferencesKey("api token")
    }
}
