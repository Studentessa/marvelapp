package com.mmartinez.feature_character_list


import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.mmartinez.data.domain.*


import com.mmartinez.feature_character_list.domain.usecases.GetCharacterListUseCase
import com.mmartinez.feature_character_list.view.CharacterListViewModel
import com.mmartinez.feature_character_list.view.CharacterListViewModelState

import junit.framework.Assert.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher

import kotlinx.coroutines.test.resetMain
import org.junit.Before
import org.junit.Test
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Rule
import org.junit.runner.RunWith
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class CharacterListtViewModelTest {

    private val testDispatcher = TestCoroutineDispatcher()
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()
    private val getCharacterListUseCase: GetCharacterListUseCase = mock()

    private lateinit var viewModel: CharacterListViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        viewModel = CharacterListViewModel(
            getCharacterListUseCase
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()// reset main dispatcher to the original Main dispatcher
        testDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun `given getCharacterList success, expect CharacterListViewModelState Success state`() = runBlockingTest {
            //ARRANGE
            val resultData = Fixtures.CharacterListDomainModel.Data.model
            val params = GetCharacterListUseCase.Params(0, null)
            val result: DomainResponse.Success<CharacterListDModel, MarvelErrorDModel> = DomainResponse.Success(resultData)

            doReturn(result).`when`(getCharacterListUseCase).invoke(params)

            //ACT
            viewModel.notifiedLastVisible.value = 20

            //ASSERT
            verify(getCharacterListUseCase, times(2)).invoke(params)
            assert(viewModel.state.value is CharacterListViewModelState.Success)
            assertEquals(resultData.list, viewModel.characterList)
    }

    @Test
    fun `given getCharacterList success and list size zero, expect CharacterListViewModelState Empty state`() = runBlockingTest {
        //ARRANGE
        val resultData: CharacterListDModel = mock()
        val params = GetCharacterListUseCase.Params(0, null)
        val result: DomainResponse.Success<CharacterListDModel, MarvelErrorDModel> = mock {
            on { this.model } doReturn resultData
        }
        doReturn(result).`when`(getCharacterListUseCase).invoke(params)

        //ACT
        viewModel.notifiedLastVisible.value = 20

        //ASSERT
        verify(getCharacterListUseCase, times(2)).invoke(params)
        assert(viewModel.state.value is CharacterListViewModelState.Empty)
    }

    @Test
    fun `given getCharacterList fails, expect failure to be handled`() = runBlockingTest {
        //ARRANGE
        val resultFailure: MarvelErrorDModel = mock()
        val params = GetCharacterListUseCase.Params(0, null)
        val resultData: DomainResponse.Error<CharacterListDModel, MarvelErrorDModel> = DomainResponse.Error(resultFailure)

        doReturn(resultData).`when`(getCharacterListUseCase).invoke(params)

        //ACT
        viewModel.notifiedLastVisible.value = 20

        //ASSERT
        verify(getCharacterListUseCase, times(2)).invoke(params)
        assert(viewModel.state.value is CharacterListViewModelState.Error)
    }
}