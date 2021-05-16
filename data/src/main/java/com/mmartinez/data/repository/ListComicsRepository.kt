package com.mmartinez.data.repository

import com.mmartinez.data.HashGenerator
import com.mmartinez.data.di.PrivateKey
import com.mmartinez.data.di.PublicKey
import com.mmartinez.data.domain.*
import com.mmartinez.data.mapper.CharacterListMapper
import com.mmartinez.data.mapper.ComicListMapper
import com.mmartinez.data.rest.MarvelService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.http.Query
import java.io.IOException
import javax.inject.Inject

class ListComicsRepository @Inject constructor(
    private val marvelService: MarvelService,
    private val mapper: ComicListMapper,
    private val hashGenerator: HashGenerator,
    @PublicKey private val publicKey: String,
    @PrivateKey private val privateKey: String,
){
    suspend fun  getComicList(
        characterId: Int
    ): DomainResponse<ComicListDModel, MarvelErrorDModel> = withContext(Dispatchers.IO) {
        val timestamp = System.currentTimeMillis()
        val has =  hashGenerator.buildMD5Digest("$timestamp$privateKey$publicKey")
        try {
            val response = marvelService.getComicFromCharacter(characterId, timestamp, has)
            if (response.isSuccessful && response.body()!= null) {
                val domainList: ComicListDModel =  mapper.toDomainModel(response.body()!!)
                DomainResponse.Success(domainList)
            }else{
                DomainResponse.Error(mapper.toErrorDomainModel(response.message()))
            }
        }catch (e: IOException){
            DomainResponse.Error(mapper.toErrorDomainModel(e))
        }
    }
}