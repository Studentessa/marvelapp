package com.mmartinez.data.mapper

import com.mmartinez.data.domain.CharacterListDModel
import com.mmartinez.data.domain.CharacterDModel
import com.mmartinez.data.domain.MarvelErrorDModel
import com.mmartinez.data.domain.ErrorCode

import com.mmartinez.data.model.DataList
import com.mmartinez.data.model.ResponseData
import java.io.IOException

class CharacterListMapper {

    fun toDomainModel(list: ResponseData<DataList>): CharacterListDModel{
        return CharacterListDModel(
            offset = list.data.offset,
            count = list.data.count,
            list = list.data.result.map { CharacterDModel(
                id = it.id,
                name = it.name,
                description = it.description,
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