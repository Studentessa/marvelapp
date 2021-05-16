package com.mmartinez.feature_character_detail.domain.usecases

import com.mmartinez.data.domain.CharacterListDModel
import com.mmartinez.data.domain.DomainResponse
import com.mmartinez.data.domain.MarvelErrorDModel
import com.mmartinez.data.repository.DetailCharacterRepository
import com.mmartinez.data.repository.ListCharacterRepository
import com.mmartinez.data.repository.ListComicsRepository
import javax.inject.Inject

class GetCharacterDetailUseCase @Inject constructor(
    private val detailCharacterRepository: DetailCharacterRepository,
    private val listComicsRepository: ListComicsRepository
){
    data class Params(
        val characterId: Int
    )

    suspend operator fun invoke(params: Params): DomainResponse<CharacterListDModel, MarvelErrorDModel>{
        val response =  detailCharacterRepository.getDetail(params.characterId)
        if(response is DomainResponse.Success){
            listComicsRepository.getComicList(params.characterId).apply {
                if(this is DomainResponse.Success){
                    response.model.list[0].comicList = this.model
                }
            }
        }
        return response
    }
}