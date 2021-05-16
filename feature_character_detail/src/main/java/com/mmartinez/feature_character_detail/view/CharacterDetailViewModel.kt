package com.mmartinez.feature_character_detail.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mmartinez.data.domain.DomainResponse
import com.mmartinez.feature_character_detail.domain.usecases.GetCharacterDetailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterDetailViewModel @Inject constructor(
    val getCharacterDetailUseCase: GetCharacterDetailUseCase
): ViewModel(){
    var selectedCharacterId = MutableStateFlow(0)
    private val _detailStateFlow = MutableStateFlow<CharacterDetailViewModelState>(CharacterDetailViewModelState.LoadingData)
    val state: StateFlow<CharacterDetailViewModelState>
        get() = _detailStateFlow

    init {
        viewModelScope.launch {
            selectedCharacterId.collect {
                if (it != 0) getDetailCharacter(it)
            }
        }
    }

    private suspend fun getDetailCharacter(id: Int){
        getCharacterDetailUseCase
        val params = GetCharacterDetailUseCase.Params(characterId = id)
        when(val result = getCharacterDetailUseCase(params)){
            is DomainResponse.Success -> {
                if(result.model.list.isNotEmpty()){
                    _detailStateFlow.value = (CharacterDetailViewModelState.Success(result.model))
                }else{
                    _detailStateFlow.value = (CharacterDetailViewModelState.Empty)
                }
            }
            is DomainResponse.Error -> {
                _detailStateFlow.value = (CharacterDetailViewModelState.Error)
            }
            else  ->  _detailStateFlow.value = (CharacterDetailViewModelState.LoadingData)
        }
    }
}