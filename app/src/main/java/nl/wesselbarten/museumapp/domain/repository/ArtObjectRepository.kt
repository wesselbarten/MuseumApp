package nl.wesselbarten.museumapp.domain.repository

import kotlinx.coroutines.flow.Flow
import nl.wesselbarten.museumapp.data.ResultWrapper
import nl.wesselbarten.museumapp.domain.model.ArtObject
import nl.wesselbarten.museumapp.domain.model.ExtendedArtObject

interface ArtObjectRepository {

    fun getAll(): Flow<ResultWrapper<List<ArtObject>>>

    suspend fun fetchNextPage(): ResultWrapper<Unit>

    suspend fun refreshAll(): ResultWrapper<Unit>

    suspend fun findByObjectNumber(objectNumber: String): ResultWrapper<ExtendedArtObject>
}