package com.sb.moovich.presentation.all.ui

import com.sb.moovich.domain.entity.Collection

sealed interface AllCollectionsState {
    data object Loading: AllCollectionsState
    data class Collections(val collections: List<Collection>): AllCollectionsState
}