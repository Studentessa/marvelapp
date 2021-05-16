package com.mmartinez.feature_character_detail.view.comiclist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mmartinez.data.domain.ComicDModel


class ComicListAdapter internal constructor(private val characterList: List<ComicDModel>) :
    RecyclerView.Adapter<ComicViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ComicViewHolder {
        return ComicViewHolder(
            LayoutInflater.from(
                parent.context
            ), parent
        )
    }

    override fun onBindViewHolder(holder: ComicViewHolder, position: Int) {
        holder.bindView(characterList[position], holder.itemView.context)
    }

    override fun getItemCount(): Int {
        return characterList.size
    }
}