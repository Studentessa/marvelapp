package com.mmartinez.data.rest

import com.mmartinez.data.model.ComicList
import com.mmartinez.data.model.ComicListResponse
import com.mmartinez.data.model.DataList
import com.mmartinez.data.model.ResponseData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface MarvelService {

        @GET("characters")
        suspend fun getCharacterList(
                @Query("ts") ts: Long,
                @Query("hash") md5Digest: String,
                @Query("offset")  offset: Int?,
                @Query("nameStartsWith") nameStartsWith: String?
        ): Response<ResponseData<DataList>>

        @GET("characters/{characterId}")
        suspend fun getCharacterDetail(
                @Path("characterId") characterId: Int,
                @Query("ts") ts: Long,
                @Query("hash") md5Digest: String,

        ): Response<ResponseData<DataList>>

        @GET("characters/{characterId}/comics")
        suspend fun getComicFromCharacter(
                @Path("characterId") characterId: Int,
                @Query("ts") ts: Long,
                @Query("hash") md5Digest: String,

                ): Response<ResponseData<ComicList>>

}