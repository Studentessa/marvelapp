package com.mmartinez.feature_character_list.view

import com.mmartinez.data.domain.CharacterListDModel

sealed class CharacterListViewModelState {
    object Empty : CharacterListViewModelState()
    data class Success(val characterListDModel: CharacterListDModel): CharacterListViewModelState()
    object LoadingList: CharacterListViewModelState()
    object Error : CharacterListViewModelState()
}