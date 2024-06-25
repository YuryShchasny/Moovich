package com.sb.moovich.data.mapper

interface IDataMapper<D, E> {
    suspend fun mapDataToEntity(data: D): E
    suspend fun mapEntityToData(entity: E): D
}