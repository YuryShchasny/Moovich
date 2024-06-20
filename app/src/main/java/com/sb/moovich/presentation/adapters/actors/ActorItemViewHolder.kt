package com.sb.moovich.presentation.adapters.actors

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sb.moovich.R

class ActorItemViewHolder(
    view: View,
) : RecyclerView.ViewHolder(view) {
    val imageViewActorPhoto: ImageView = view.findViewById(R.id.imageViewActorPhoto)
    val textViewActorFirstName: TextView = view.findViewById(R.id.textViewActorFirstName)
    val textViewActorLastName: TextView = view.findViewById(R.id.textViewActorLastName)
    val textViewActorDescription: TextView = view.findViewById(R.id.textViewActorDescription)
}
