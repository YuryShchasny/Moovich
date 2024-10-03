package com.sb.moovich.presentation.home.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.sb.moovich.core.extensions.load
import com.sb.moovich.domain.entity.Collection
import com.sb.moovich.presentation.home.databinding.ItemCollectionBinding

class CollectionsAdapter(private val collections: List<Collection>, private val onClickListener: (Collection) -> Unit) :
    RecyclerView.Adapter<CollectionViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CollectionViewHolder =
        CollectionViewHolder.from(parent)

    override fun getItemCount(): Int = collections.size

    override fun onBindViewHolder(holder: CollectionViewHolder, position: Int) {
        holder.bind(collections[position], onClickListener)
    }
}

class CollectionViewHolder(private val binding: ItemCollectionBinding) : ViewHolder(binding.root) {

    fun bind(collection: Collection, onClickListener: (Collection) -> Unit) {
        binding.card.setOnClickListener { onClickListener(collection) }
        binding.image.load(collection.cover)
        binding.name.text = collection.name
    }

    companion object {
        fun from(parent: ViewGroup): CollectionViewHolder {
            return CollectionViewHolder(
                ItemCollectionBinding.inflate(
                    android.view.LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }
    }
}