package nl.wesselbarten.museumapp.domain.usecase

import nl.wesselbarten.museumapp.data.ResultWrapper
import nl.wesselbarten.museumapp.domain.repository.ArtObjectRepository
import javax.inject.Inject

class RefreshArtObjectsUseCase @Inject constructor(
    private val artObjectRepository: ArtObjectRepository
){

    suspend fun execute(): ResultWrapper<Unit> {
        return artObjectRepository.refreshAll()
    }
}