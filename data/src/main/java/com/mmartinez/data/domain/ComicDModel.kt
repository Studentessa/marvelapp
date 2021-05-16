package com.mmartinez.data.domain


class ComicListDModel(
    val count: Int,
    val list: List<ComicDModel>
)
data class ComicDModel(
    val id: Int,
    val title: String,
    val description: String?,
    val variantDescription: String,
    val imageUrl: String
)