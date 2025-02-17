package com.sb.moovich.data.repository

import com.sb.moovich.data.local.datasource.CacheLocalDataSource
import com.sb.moovich.domain.repository.ProfileRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

class ProfileRepositoryImpl @Inject constructor(
    private val cacheLocalDataSource: CacheLocalDataSource
) : ProfileRepository {
    private val cache = MutableStateFlow(cacheLocalDataSource.getAppCacheSize())

    override suspend fun getCacheSize(): StateFlow<Long> = cache

    override suspend fun clearCache() {
        cacheLocalDataSource.clearAppCache()
        cache.update { cacheLocalDataSource.getAppCacheSize() }
    }

    override suspend fun updateSize() {
        cache.update { cacheLocalDataSource.getAppCacheSize() }
    }
}
