package com.mmartinez.feature_character_detail.view.comiclist

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.size.Scale
import coil.transform.RoundedCornersTransformation
import com.mmartinez.data.domain.CharacterDModel
import com.mmartinez.data.domain.ComicDModel
import com.mmartinez.feature_character_detail.R


class ComicViewHolder internal constructor(
    inflater: LayoutInflater,
    parent: ViewGroup
) : RecyclerView.ViewHolder(
    inflater.inflate(
        R.layout.fragment_item_list_comic,
        parent,
        false
    )
) {
    fun bindView(comic: ComicDModel, context: Context) {
        imgCharacter.load(comic.imageUrl){
            placeholder(R.drawable.ic_captain_america)
            scale(Scale.FILL)
            transformations(RoundedCornersTransformation(16f))
        }
        comicTitle.text = comic.title
    }

    private val imgCharacter: ImageView = itemView.findViewById(R.id.imageView)
    private val comicTitle: TextView = itemView.findViewById(R.id.textViewTitle)
}