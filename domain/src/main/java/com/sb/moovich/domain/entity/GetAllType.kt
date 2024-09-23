package com.sb.moovich.domain.entity

import java.io.Serializable

sealed interface GetAllType : Serializable {
    data object Recommendations : GetAllType {
        private fun readResolve(): Any = Recommendations
    }

    data object Series : GetAllType {
        private fun readResolve(): Any = Series
    }

    data class Genre(val genre: String) : GetAllType

    data class Collection(val slug: String): GetAllType
}