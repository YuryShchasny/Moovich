package com.sb.moovich.data.remote.datasource

import com.sb.moovich.data.di.MovieApiProvide
import com.sb.moovich.data.mapper.CollectionDtoMapper
import com.sb.moovich.data.remote.api.MovieApi
import com.sb.moovich.data.remote.pagination.IPaginator
import com.sb.moovich.data.remote.pagination.Paginator
import com.sb.moovich.domain.entity.Collection
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CollectionRemoteDataSource @Inject constructor(
    @MovieApiProvide private val movieApi: MovieApi,
    private val collectionDtoMapper: CollectionDtoMapper,
) {
    private var collectionPaginator: IPaginator<Collection>? = null

    suspend fun getCollections(): List<Collection> {
        val response = movieApi.getCollections()
        if (response.isSuccessful) {
            response.body()?.let {
                return collectionDtoMapper.mapDataToEntityList(it.docs ?: emptyList())
            }
        }
        return emptyList()
    }

    suspend fun getAllCollections(): Flow<List<Collection>> {
        collectionPaginator = Paginator(
            request = { page ->
                movieApi.getCollections(page = page, category = null)
                    .body()?.docs?.filter { (it.count ?: 0) > 0 } ?: emptyList()
            },
            mapper = collectionDtoMapper,
        )
        return collectionPaginator!!.data
    }

    suspend fun collectionNextPage() {
        collectionPaginator?.nextPage()
    }
}