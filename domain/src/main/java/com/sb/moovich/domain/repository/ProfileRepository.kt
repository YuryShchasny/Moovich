package com.sb.moovich.domain.repository

import kotlinx.coroutines.flow.StateFlow

interface ProfileRepository {
    suspend fun getCacheSize(): StateFlow<Long>
    suspend fun clearCache()
    suspend fun updateSize()
}