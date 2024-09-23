package com.sb.moovich.data.remote.pagination

import kotlinx.coroutines.flow.Flow

interface IPaginator<Entity> {
    val data: Flow<List<Entity>>
    fun nextPage()
}