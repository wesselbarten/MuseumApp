package nl.wesselbarten.museumapp.data.source

import kotlinx.coroutines.withContext
import nl.wesselbarten.museumapp.data.ResultWrapper
import nl.wesselbarten.museumapp.data.network.ArtCollectionApiService
import nl.wesselbarten.museumapp.domain.model.ArtObject
import nl.wesselbarten.museumapp.domain.model.ExtendedArtObject
import nl.wesselbarten.museumapp.util.coroutines.DispatcherProvider
import timber.log.Timber

class RemoteArtObjectsDataSource(
    private val artCollectionApiService: ArtCollectionApiService,
    private val dispatcherProvider: DispatcherProvider
) : ArtObjectsDataSource {

    override suspend fun getAll(page: Int): ResultWrapper<List<ArtObject>> {
        return withContext(dispatcherProvider.io()) {
            try {
                val response = artCollectionApiService.findAll(page)
                val result = response.artObjects.map { it.toDomainModel() }
                ResultWrapper.Success(result)
            } catch (e: Exception) {
                Timber.e(e,"Unable to get all art objects.")
                ResultWrapper.Error(e)
            }
        }
    }

    override suspend fun findByObjectNumber(objectNumber: String): ResultWrapper<ExtendedArtObject> {
        return withContext(dispatcherProvider.io()) {
            try {
                val response = artCollectionApiService.findByObjectNumber(objectNumber)
                ResultWrapper.Success(response.artObject.toDomainModel())
            } catch (e: Exception) {
                Timber.e(e, "Unable to find art object by object number.")
                ResultWrapper.Error(e)
            }
        }
    }
}