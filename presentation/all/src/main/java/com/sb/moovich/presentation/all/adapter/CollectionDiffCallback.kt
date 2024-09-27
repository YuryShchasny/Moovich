package com.sb.moovich.presentation.all.adapter

import androidx.recyclerview.widget.DiffUtil

class CollectionDiffCallback : DiffUtil.ItemCallback<CollectionItem>() {
    override fun areItemsTheSame(oldItem: CollectionItem, newItem: CollectionItem): Boolean {
        return if (oldItem is CollectionItem.Info && newItem is CollectionItem.Info) oldItem.collection.slug == newItem.collection.slug
        else oldItem === newItem
    }

    override fun areContentsTheSame(oldItem: CollectionItem, newItem: CollectionItem): Boolean {
        return if (oldItem is CollectionItem.Info && newItem is CollectionItem.Info) oldItem.collection == newItem.collection
        else oldItem == newItem
    }
}