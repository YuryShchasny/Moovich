package com.sb.moovich.presentation.all.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.sb.moovich.domain.entity.Collection
import com.sb.moovich.presentation.all.databinding.ItemAllCollectionBinding

class AllCollectionsAdapter: ListAdapter<Collection,AllCollectionsViewHolder>(CollectionDiffCallback()) {

    var onCollectionItemClickListener: ((Collection) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllCollectionsViewHolder = AllCollectionsViewHolder.from(parent)

    override fun onBindViewHolder(holder: AllCollectionsViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
        holder.itemView.setOnClickListener {
            onCollectionItemClickListener?.invoke(item)
        }
    }
}

class AllCollectionsViewHolder(private val binding: ItemAllCollectionBinding): RecyclerView.ViewHolder(binding.root) {
    fun bind(collection: Collection) {
        binding.name.text = collection.name
        binding.count.text = binding.root.context.getString(com.sb.moovich.core.R.string.count, collection.count)
        binding.image.load(collection.cover)
    }

    companion object {
        fun from(parent: ViewGroup): AllCollectionsViewHolder {
           return AllCollectionsViewHolder(
               ItemAllCollectionBinding.inflate(
                   LayoutInflater.from(parent.context),
                   parent,
                   false
               )
           )
        }
    }
}

class CollectionDiffCallback(): DiffUtil.ItemCallback<Collection>() {
    override fun areItemsTheSame(oldItem: Collection, newItem: Collection): Boolean {
        return oldItem.slug == newItem.slug
    }

    override fun areContentsTheSame(oldItem: Collection, newItem: Collection): Boolean {
        return oldItem == newItem
    }
}