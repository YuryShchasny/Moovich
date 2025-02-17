package com.sb.moovich.presentation.all.adapter

import com.sb.moovich.domain.entity.Collection

sealed interface CollectionItem {
    data class Info(val collection: Collection): CollectionItem
    data object Shimmer: CollectionItem
}