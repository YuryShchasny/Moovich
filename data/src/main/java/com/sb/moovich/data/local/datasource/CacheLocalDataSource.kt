package com.sb.moovich.data.local.datasource

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.File
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CacheLocalDataSource @Inject constructor(
    @ApplicationContext private val context: Context
) {
    fun getAppCacheSize(): Long {
        var size: Long = 0
        val cacheDir = context.cacheDir
        val codeCacheDir = context.codeCacheDir
        if (cacheDir.exists()) size += getDirSize(cacheDir)
        if (codeCacheDir.exists()) size += getDirSize(codeCacheDir)
        return size
    }

    private fun getDirSize(dir: File): Long {
        var size: Long = 0
        if (dir.isDirectory) {
            for (file in dir.listFiles()!!) {
                size += if (file.isFile) {
                    file.length()
                } else {
                    getDirSize(file)
                }
            }
        } else {
            size = dir.length()
        }
        return size
    }

    fun clearAppCache() {
        context.cacheDir.deleteRecursively()
        context.codeCacheDir.deleteRecursively()
    }
}
