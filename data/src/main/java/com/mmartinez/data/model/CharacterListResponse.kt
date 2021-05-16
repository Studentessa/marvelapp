package com.mmartinez.data.model

import com.google.gson.annotations.SerializedName

data class DataList(
    val offset: Int,
    val count: Int,
    @SerializedName("results")
    val result: List<Character>
)

data class Character(
    val id: Int,
    val name: String,
    val description: String,
    @SerializedName("thumbnail")
    val image: ImageData
)