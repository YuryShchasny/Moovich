package com.sb.moovich.presentation.all.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sb.moovich.domain.entity.Collection

class AllCollectionsAdapter :
    ListAdapter<CollectionItem, RecyclerView.ViewHolder>(CollectionDiffCallback()) {

    var onCollectionItemClickListener: ((Collection) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        when (viewType) {
            INFO_TYPE -> AllCollectionsViewHolder.from(parent)
            SHIMMER_TYPE -> AllCollectionShimmerViewHolder.from(parent)
            else -> throw IllegalArgumentException()
        }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val collection = currentList[position]) {
            is CollectionItem.Info -> {
                (holder as AllCollectionsViewHolder).bind(collection.collection)
                onCollectionItemClickListener?.let { listener ->
                    holder.itemView.setOnClickListener {
                        listener.invoke(collection.collection)
                    }
                }
            }

            CollectionItem.Shimmer -> (holder as AllCollectionShimmerViewHolder).bind()
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (currentList[position]) {
            is CollectionItem.Info -> INFO_TYPE
            CollectionItem.Shimmer -> SHIMMER_TYPE
        }
    }

    fun submit(list: List<Collection>, withShimmer: Boolean = false) {
        val mappedList = list.map { CollectionItem.Info(it) }
        val resultList = if(withShimmer) mappedList + CollectionItem.Shimmer else mappedList
        submitList(resultList)
    }

    fun submit(list: List<Collection>, withShimmer: Boolean = false, commitCallback: Runnable?) {
        val mappedList = list.map { CollectionItem.Info(it) }
        val resultList = if(withShimmer) mappedList + CollectionItem.Shimmer else mappedList
        submitList(resultList, commitCallback)
    }

    companion object {
        private const val INFO_TYPE = 0
        private const val SHIMMER_TYPE = 1
    }
}
