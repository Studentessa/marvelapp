package com.mmartinez.feature_character_list

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
}