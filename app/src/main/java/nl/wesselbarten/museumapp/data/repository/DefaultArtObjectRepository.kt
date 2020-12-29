package nl.wesselbarten.museumapp.data.repository

import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.withContext
import nl.wesselbarten.museumapp.data.ResultWrapper
import nl.wesselbarten.museumapp.data.source.ArtObjectsDataSource
import nl.wesselbarten.museumapp.domain.model.ArtObject
import nl.wesselbarten.museumapp.domain.model.ExtendedArtObject
import nl.wesselbarten.museumapp.domain.repository.ArtObjectRepository
import nl.wesselbarten.museumapp.util.coroutines.DispatcherProvider
import nl.wesselbarten.museumapp.util.isNull
import timber.log.Timber
import javax.inject.Inject

private const val INITIAL_PAGE = 1
const val ART_OBJECTS_PAGE_SIZE = 10

@Suppress("EXPERIMENTAL_API_USAGE")
class DefaultArtObjectRepository @Inject constructor(
    private val dispatcherProvider: DispatcherProvider,
    private val artObjectsDataSource: ArtObjectsDataSource
) : ArtObjectRepository {

    private val artObjectsChannel = ConflatedBroadcastChannel<ResultWrapper<List<ArtObject>>>()

    private var getAllPage: Int = INITIAL_PAGE

    override fun getAll(): Flow<ResultWrapper<List<ArtObject>>> {
        return flow {
            if (artObjectsChannel.valueOrNull.isNull()) {
                getAllPage = INITIAL_PAGE
                val result = artObjectsDataSource.getAll(page = getAllPage)
                artObjectsChannel.send(result)

                if (result is ResultWrapper.Success) {
                    getAllPage++
                }
            }

            emitAll(artObjectsChannel.asFlow())

        }.flowOn(dispatcherProvider.default())
    }

    override suspend fun fetchNextPage(): ResultWrapper<Unit> {
        return withContext(dispatcherProvider.default()) {
            Timber.d("Fetching page $getAllPage")

            val result = artObjectsDataSource.getAll(page = getAllPage)
            val oldResult = artObjectsChannel.value

            when (result) {
                is ResultWrapper.Success -> {
                    if (oldResult is ResultWrapper.Success) {
                        val combinedList = oldResult.data + result.data
                        artObjectsChannel.send(ResultWrapper.Success(combinedList))
                        getAllPage++
                    }
                    ResultWrapper.Success(Unit)
                }
                is ResultWrapper.Error -> result
            }
        }
    }

    override suspend fun refreshAll(): ResultWrapper<Unit> {
        getAllPage = INITIAL_PAGE
        return when (val result = artObjectsDataSource.getAll(page = getAllPage)) {
            is ResultWrapper.Success -> {
                artObjectsChannel.send(result)
                ResultWrapper.Success(Unit)
            }
            is ResultWrapper.Error -> result
        }
    }

    override suspend fun findByObjectNumber(objectNumber: String): ResultWrapper<ExtendedArtObject> {
        return artObjectsDataSource.findByObjectNumber(objectNumber)
    }
}