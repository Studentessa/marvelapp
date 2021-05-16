package com.mmartinez.feature_character_list.view

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mmartinez.data.domain.CharacterDModel
import com.mmartinez.data.domain.DomainResponse
import com.mmartinez.feature_character_list.domain.usecases.GetCharacterListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterListViewModel @Inject constructor(
    private val getCharacterListUseCase: GetCharacterListUseCase
): ViewModel(){
    val notifiedLastVisible = MutableStateFlow(0)
    private val _listLiveData = MutableStateFlow<CharacterListViewModelState>(CharacterListViewModelState.LoadingList)
    val state: StateFlow<CharacterListViewModelState>
        get() = _listLiveData
    private var count = 0
    val characterList: ArrayList<CharacterDModel> = ArrayList()
    init {
        viewModelScope.launch {
            notifiedLastVisible.collect {
                if ( it >= (count -4)) {
                    getCharacterList()
                }
            }
        }
    }

    private suspend fun getCharacterList(){
        val params = GetCharacterListUseCase.Params(offset = count, nameStartsWith = null)
        when(val result = getCharacterListUseCase(params)){
            is DomainResponse.Success -> {
                if(result.model.list.isNotEmpty()){
                    characterList.addAll(characterList.lastIndex +1, result.model.list)
                    _listLiveData.value = (CharacterListViewModelState.Success(result.model))
                    count = result.model.count + result.model.offset
                }else if(count == 0) {
                        _listLiveData.value = (CharacterListViewModelState.Empty)
                }else {
                }
            }
            is DomainResponse.Error -> {
                _listLiveData.value = (CharacterListViewModelState.Error)
            }
            else  ->  _listLiveData.value = (CharacterListViewModelState.LoadingList)
        }
    }
}