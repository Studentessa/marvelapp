package com.mmartinez.data.mapper

import com.mmartinez.data.domain.*
import com.mmartinez.data.model.ComicList
import com.mmartinez.data.model.ComicListResponse
import com.mmartinez.data.model.ResponseData
import java.io.IOException

class ComicListMapper {

    fun toDomainModel(list: ResponseData<ComicList>): ComicListDModel{
        return ComicListDModel(
            count = list.data.count,
            list = list.data.comicList.map { ComicDModel(
                id = it.id,
                title = it.title,
                description = it.description,
                variantDescription = it.variantDescription,
                imageUrl = it.image.path+"."+it.image.extension)
             }
        )
    }

    fun toErrorDomainModel(message: String): MarvelErrorDModel {
        return MarvelErrorDModel(ErrorCode.Unknown, message)
    }

    fun toErrorDomainModel(exception: Exception): MarvelErrorDModel {
        val errorCode = when (exception) {
            is IOException -> ErrorCode.ServerNotReachable
            else -> ErrorCode.Unknown
        }
        return MarvelErrorDModel(errorCode, exception.message)
    }
}