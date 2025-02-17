package com.sb.moovich.data.mapper

import com.sb.moovich.data.remote.dto.CollectionDto
import com.sb.moovich.domain.entity.Collection
import javax.inject.Inject

class CollectionDtoMapper @Inject constructor(): IDataMapper<CollectionDto, Collection> {
    override suspend fun mapDataToEntity(data: CollectionDto): Collection {
        return Collection(
            slug = data.slug ?: "",
            name = data.name ?: "",
            count = data.count ?: 0,
            cover = data.cover?.url
        )
    }

    suspend fun mapDataToEntityList(data: List<CollectionDto>): List<Collection> =
        data.filter { !it.slug.isNullOrBlank() && !it.name.isNullOrBlank() }.map { mapDataToEntity(it) }

    override suspend fun mapEntityToData(entity: Collection): CollectionDto {
        throw UnsupportedOperationException()
    }
}