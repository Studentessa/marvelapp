package com.mmartinez.data.domain

class CharacterListDModel(
    val offset: Int,
    val count: Int,
    val list: List<CharacterDModel>
)

data class CharacterDModel(
    val id: Int,
    val name: String,
    val description: String?,
    val imageUrl: String,
    var comicList: ComicListDModel ?=null
)

data class ImageDModel(
    val path: String,
    val extension: String
)