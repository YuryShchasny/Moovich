package com.sb.moovich.presentation.all.ui.model

import com.sb.moovich.domain.entity.Collection

interface AllCollectionsFragmentEvent {
    data class OnCollectionClick(val collection: Collection): AllCollectionsFragmentEvent
}