package com.mmartinez.feature_character_list.domain.usecases

import com.mmartinez.data.domain.CharacterListDModel
import com.mmartinez.data.domain.DomainResponse
import com.mmartinez.data.domain.MarvelErrorDModel
import com.mmartinez.data.repository.ListCharacterRepository
import javax.inject.Inject

class GetCharacterListUseCase @Inject constructor(
    private val listCharacterRepository: ListCharacterRepository
){
    data class Params(
        val offset: Int?,
        val nameStartsWith: String?
    )

    suspend operator fun invoke(params: Params): DomainResponse<CharacterListDModel, MarvelErrorDModel>{
        return listCharacterRepository.getCharacterList(params.offset, params.nameStartsWith)
    }
}