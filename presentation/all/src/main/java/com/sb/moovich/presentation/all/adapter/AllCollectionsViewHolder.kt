package com.sb.moovich.presentation.all.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sb.moovich.core.extensions.loadCoil
import com.sb.moovich.domain.entity.Collection
import com.sb.moovich.presentation.all.databinding.ItemAllCollectionBinding

class AllCollectionsViewHolder(private val binding: ItemAllCollectionBinding): RecyclerView.ViewHolder(binding.root) {
    fun bind(collection: Collection) {
        binding.name.text = collection.name
        binding.count.text = binding.root.context.getString(com.sb.moovich.core.R.string.count, collection.count)
        binding.image.loadCoil(collection.cover)
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