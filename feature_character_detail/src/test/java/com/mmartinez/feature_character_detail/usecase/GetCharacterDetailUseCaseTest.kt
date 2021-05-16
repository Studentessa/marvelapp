package com.mmartinez.feature_character_detail.usecase

import com.mmartinez.data.domain.DomainResponse
import com.mmartinez.data.repository.DetailCharacterRepository
import com.mmartinez.data.repository.ListComicsRepository
import com.mmartinez.feature_character_detail.Fixtures
import com.mmartinez.feature_character_detail.domain.usecases.GetCharacterDetailUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class GetCharacterDetailUseCaseTest {
    private lateinit var getCharacterDetailUseCase: GetCharacterDetailUseCase

    private val detailCharacterRepository: DetailCharacterRepository = mock()
    private val listComicsRepository: ListComicsRepository = mock()

    @Before
    fun setUp() {
        getCharacterDetailUseCase = GetCharacterDetailUseCase(
            detailCharacterRepository,
            listComicsRepository
        )
    }

    @Test
    fun `getCharacterDetailUseCase with success`() = runBlockingTest {
        val detailDomain = Fixtures.CharacterListDomainModel.Data.model
        val comicListDomain = Fixtures.ComicListDomainModel.Data.model

        whenever(detailCharacterRepository.getDetail(1234)).thenReturn(DomainResponse.Success(detailDomain))
        whenever(listComicsRepository.getComicList(1234)).thenReturn(DomainResponse.Success(comicListDomain))

        val result = getCharacterDetailUseCase(GetCharacterDetailUseCase.Params(1234))

        assert(result is DomainResponse.Success)
        assert((result as DomainResponse.Success).model.list.isNotEmpty())
        assert((result).model.list[0].comicList?.list?.isNotEmpty() == true)
    }
}