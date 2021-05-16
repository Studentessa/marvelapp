package com.mmartinez.data.mapper

import com.mmartinez.data.domain.ErrorCode
import com.mmartinez.data.mock.Fixtures
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertSame
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import java.io.IOException

@RunWith(MockitoJUnitRunner.StrictStubs::class)
class CharacterMapperTest {
    private lateinit var cut: CharacterListMapper

    companion object {
        private val DATA_MODEL = Fixtures.CharacterList.Data.model
        private val DOMAIN_MODEL = Fixtures.CharacterListDomainModel.Data.model
    }

    @Before
    fun setUp() {
        cut = CharacterListMapper()
    }

    @Test
    fun ` data model is correctly mapped to domain model`() {
        val domainModel = cut.toDomainModel(DATA_MODEL)

        domainModel.run {
            assertEquals(domainModel.count, DOMAIN_MODEL.count)
            val item = this.list.first()
            assertEquals(item.id, DOMAIN_MODEL.list[0].id)
            assertEquals(item.description, DOMAIN_MODEL.list[0].description)
            assertEquals(item.name, DOMAIN_MODEL.list[0].name)
            assertEquals(item.imageUrl, DOMAIN_MODEL.list[0].imageUrl)
            assertEquals(item.comicList, null)
        }
    }

    @Test
    fun `IOException is mapped to  ServerNotReachable error code`() {
        val ioException = IOException()

        val domainModel = cut.toErrorDomainModel(ioException)

        with(domainModel) {
            assertEquals(message, ioException.message)
            assertSame(errorCode, ErrorCode.ServerNotReachable)
        }
    }

    @Test
    fun `String errror message is correctly mapped to ResponseErrorModel error domain model`() {
        val responseErrorModel = "unknown error code"

        val domainModel = cut.toErrorDomainModel(responseErrorModel)

        with(domainModel) {
            assertEquals(message, responseErrorModel)
            assertEquals(errorCode, ErrorCode.Unknown)
        }
    }
}