package nl.wesselbarten.museumapp.data.source

import nl.wesselbarten.museumapp.data.ResultWrapper
import nl.wesselbarten.museumapp.domain.model.ArtObject
import nl.wesselbarten.museumapp.domain.model.ExtendedArtObject

interface ArtObjectsDataSource {

    suspend fun getAll(page: Int): ResultWrapper<List<ArtObject>>

    suspend fun findByObjectNumber(objectNumber: String): ResultWrapper<ExtendedArtObject>
}