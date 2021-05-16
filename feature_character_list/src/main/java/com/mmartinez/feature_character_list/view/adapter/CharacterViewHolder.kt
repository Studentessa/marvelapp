package com.mmartinez.feature_character_list.view.adapter

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
import com.mmartinez.feature_character_list.R


class CharacterViewHolder internal constructor(
    inflater: LayoutInflater,
    parent: ViewGroup
) : RecyclerView.ViewHolder(
    inflater.inflate(
        R.layout.fragment_item_list_character,
        parent,
        false
    )
) {
    fun bindView(character: CharacterDModel, context: Context, position: Int, mListener: (CharacterDModel) -> Unit) {
        imgCharacter.load(character.imageUrl){
            placeholder(R.drawable.im_marvel_logo)
            scale(Scale.FILL)
            transformations(RoundedCornersTransformation(16f))
        }
        textName.text = character.name
        layoutParent.setOnClickListener {
            mListener(character)
        }
    }
    private val textName: TextView = itemView.findViewById(R.id.textViewName)
    private val imgCharacter: ImageView = itemView.findViewById(R.id.imageView)
    private val layoutParent: ConstraintLayout = itemView.findViewById(R.id.layoutParent)
}