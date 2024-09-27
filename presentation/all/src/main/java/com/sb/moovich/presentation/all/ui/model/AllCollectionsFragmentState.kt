package com.sb.moovich.presentation.all.ui.model

import com.sb.moovich.domain.entity.Collection

sealed interface AllCollectionsFragmentState {
    data object Loading: AllCollectionsFragmentState
    data class Collections(val collections: List<Collection>, val lastPage: Boolean = false): AllCollectionsFragmentState
}
