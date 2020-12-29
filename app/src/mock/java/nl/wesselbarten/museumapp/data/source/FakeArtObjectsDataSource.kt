package nl.wesselbarten.museumapp.data.source

import nl.wesselbarten.museumapp.data.ResultWrapper
import nl.wesselbarten.museumapp.domain.model.ArtObject
import nl.wesselbarten.museumapp.domain.model.ExtendedArtObject

class FakeArtObjectsDataSource : ArtObjectsDataSource {

    override suspend fun getAll(page: Int): ResultWrapper<List<ArtObject>> {
        TODO("Not yet implemented")
    }

    override suspend fun findByObjectNumber(objectNumber: String): ResultWrapper<ExtendedArtObject> {
        TODO("Not yet implemented")
    }
}