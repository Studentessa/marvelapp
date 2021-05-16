package com.mmartinez.data.mock

import com.mmartinez.data.domain.CharacterDModel
import com.mmartinez.data.domain.CharacterListDModel
import com.mmartinez.data.domain.ComicDModel
import com.mmartinez.data.domain.ComicListDModel
import com.mmartinez.data.model.*

class Fixtures {
    object CharacterList {
        object Data {
            val model = ResponseData(
                code = 200,
                status = "0k",
                data = DataList(
                    offset = 0,
                    count = 20,
                    result = listOf(
                        Character(
                            id = 23434,
                            name = "Spiderman",
                            description = "man",
                            image = ImageData(
                                "http://i.annihil.us/u/prod/marvel/i/mg/b/40/image_not_available",
                                "jpp"
                            )
                        )
                    )
                )
            )
        }
    }

    object CharacterListDomainModel {
        object Data {
            val model = CharacterListDModel(
                offset = 0,
                count = 20,
                list = listOf(
                    CharacterDModel(
                        id = 23434,
                        name = "Spiderman",
                        description = "man",
                        imageUrl = "http://i.annihil.us/u/prod/marvel/i/mg/b/40/image_not_available.jpp"
                    )
                )
            )
        }
    }

    object ResponseData {
        object Data {
            val model = ResponseData(
                code = 200,
                status = "0k",
                data = ComicList(
                    total = 123,
                    count = 20,
                    comicList = listOf(
                        Comic(
                            id = 23434,
                            title = "Incredible Spiderman",
                            description = "description",
                            variantDescription = "man",
                            image = ImageData(
                                "http://i.annihil.us/u/prod/marvel/i/mg/b/40/sperman_image",
                                "jpp"
                            )
                        )
                    )
                )
            )
        }
    }

    object ComicListDomainModel {
        object Data {
            val model = ComicListDModel(
                count = 20,
                list = listOf(
                    ComicDModel(
                        id = 23434,
                        title = "Incredible Spiderman",
                        description = "description",
                        variantDescription = "man",
                        imageUrl = "http://i.annihil.us/u/prod/marvel/i/mg/b/40/sperman_image.jpp"
                    )
                )
            )
        }
    }
}