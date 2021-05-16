package com.mmartinez.data.repository

import com.mmartinez.data.HashGenerator
import com.mmartinez.data.di.PrivateKey
import com.mmartinez.data.di.PublicKey
import com.mmartinez.data.mapper.CharacterListMapper
import com.mmartinez.data.domain.CharacterListDModel
import com.mmartinez.data.domain.DomainResponse
import com.mmartinez.data.domain.MarvelErrorDModel
import com.mmartinez.data.rest.MarvelService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException
import javax.inject.Inject

class ListCharacterRepository @Inject constructor(
    private val marvelService: MarvelService,
    private val mapper: CharacterListMapper,
    private val hashGenerator: HashGenerator,
    @PublicKey private val publicKey: String,
    @PrivateKey private val privateKey: String,
){
    suspend fun  getCharacterList(offset: Int?,
                                  nameStartsWith: String?
    ): DomainResponse<CharacterListDModel, MarvelErrorDModel> = withContext(Dispatchers.IO) {
        val timestamp = System.currentTimeMillis()
        val has =  hashGenerator.buildMD5Digest("$timestamp$privateKey$publicKey")
        try {
            val response = marvelService.getCharacterList(timestamp, has, offset, nameStartsWith)
            if (response.isSuccessful && response.body()!= null) {
                val domainList: CharacterListDModel =  mapper.toDomainModel(response.body()!!)
                DomainResponse.Success(domainList)
            }else{
                DomainResponse.Error(mapper.toErrorDomainModel(response.message()))
            }
        }catch (e: IOException){
            DomainResponse.Error(mapper.toErrorDomainModel(e))
        }
    }
}