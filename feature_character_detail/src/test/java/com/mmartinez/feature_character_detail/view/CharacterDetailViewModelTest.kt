package com.mmartinez.feature_character_detail.view


import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.mmartinez.data.domain.*
import com.mmartinez.feature_character_detail.Fixtures
import com.mmartinez.feature_character_detail.domain.usecases.GetCharacterDetailUseCase


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
class CharacterDetailViewModelTest {

    private val testDispatcher = TestCoroutineDispatcher()
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()
    private val getCharacterDetailUseCase: GetCharacterDetailUseCase = mock()

    private lateinit var viewModel: CharacterDetailViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        viewModel = CharacterDetailViewModel(
            getCharacterDetailUseCase
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()// reset main dispatcher to the original Main dispatcher
        testDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun `given getCharacterDetail  success, expect CharacterDetailViewModelState Success state`() = runBlockingTest {
            //ARRANGE
            val resultData =
                Fixtures.CharacterListDomainModel.Data.model
            val params = GetCharacterDetailUseCase.Params(1234)
            val result: DomainResponse.Success<CharacterListDModel, MarvelErrorDModel> = DomainResponse.Success(resultData)

            doReturn(result).`when`(getCharacterDetailUseCase).invoke(params)

            //ACT
            viewModel.selectedCharacterId.value = 1234

            //ASSERT
            verify(getCharacterDetailUseCase, times(1)).invoke(params)
            assert(viewModel.state.value is CharacterDetailViewModelState.Success)

            assertEquals(resultData, (viewModel.state.value as CharacterDetailViewModelState.Success).characterListDModel)
    }


    @Test
    fun `given getCharacterDetail success and list size zero, expect CharacterDetailViewModelState Empty state`() = runBlockingTest {
        //ARRANGE
        val resultData: CharacterListDModel = mock()
        val params = GetCharacterDetailUseCase.Params(1234)
        val result: DomainResponse.Success<CharacterListDModel, MarvelErrorDModel> = mock {
            on { this.model } doReturn resultData
        }
        doReturn(result).`when`(getCharacterDetailUseCase).invoke(params)

        //ACT
        viewModel.selectedCharacterId.value = 1234

        //ASSERT
        verify(getCharacterDetailUseCase, times(1)).invoke(params)
        assert(viewModel.state.value is CharacterDetailViewModelState.Empty)
    }

    @Test
    fun `given getCharacterDetail fails, expect failure to be handled`() = runBlockingTest {
        //ARRANGE
        val resultFailure: MarvelErrorDModel = mock()
        val params = GetCharacterDetailUseCase.Params(1234)
        val resultData: DomainResponse.Error<CharacterListDModel, MarvelErrorDModel> = DomainResponse.Error(resultFailure)

        doReturn(resultData).`when`(getCharacterDetailUseCase).invoke(params)

        //ACT
        viewModel.selectedCharacterId.value = 1234

        //ASSERT
        verify(getCharacterDetailUseCase, times(1)).invoke(params)
        assert(viewModel.state.value is CharacterDetailViewModelState.Error)
    }
}