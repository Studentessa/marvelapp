package com.mmartinez.feature_character_list.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mmartinez.data.domain.CharacterDModel


class CharacterListAdapter internal constructor(private val characterList: List<CharacterDModel>,
                                                private val mListener: (CharacterDModel) -> Unit) :
    RecyclerView.Adapter<CharacterViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        return CharacterViewHolder(
            LayoutInflater.from(
                parent.context
            ), parent
        )
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        holder.bindView(characterList[position], holder.itemView.context, position, mListener)
    }

    override fun getItemCount(): Int {
        return characterList.size
    }
}