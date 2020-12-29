package nl.wesselbarten.museumapp.domain.usecase

import kotlinx.coroutines.flow.Flow
import nl.wesselbarten.museumapp.data.ResultWrapper
import nl.wesselbarten.museumapp.domain.model.ArtObject
import nl.wesselbarten.museumapp.domain.repository.ArtObjectRepository
import javax.inject.Inject

class GetArtObjectsUseCase @Inject constructor(
    private val artObjectRepository: ArtObjectRepository
) {

    fun execute(): Flow<ResultWrapper<List<ArtObject>>> {
        return artObjectRepository.getAll()
    }
}