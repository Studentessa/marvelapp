package com.mmartinez.data.model

import com.google.gson.annotations.SerializedName

class ComicListResponse (
    val code: Int,
    val status: String,
    val data: ComicList
)

data class ComicList(
    val total: Int,
    val count: Int,
    @SerializedName("results")
    val comicList: List<Comic>
)

data class Comic(
    val id: Int,
    val title: String,
    val description: String,
    val variantDescription: String,
    @SerializedName("thumbnail")
    val image: ImageData
)