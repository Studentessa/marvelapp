package com.mmartinez.feature_character_detail.view

import com.mmartinez.data.domain.CharacterListDModel

sealed class CharacterDetailViewModelState {
    object Empty : CharacterDetailViewModelState()
    data class Success(val characterListDModel: CharacterListDModel): CharacterDetailViewModelState()
    object LoadingData: CharacterDetailViewModelState()
    object Error : CharacterDetailViewModelState()
}