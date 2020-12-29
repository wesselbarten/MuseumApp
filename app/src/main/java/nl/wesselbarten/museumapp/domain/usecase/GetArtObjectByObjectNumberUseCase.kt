package nl.wesselbarten.museumapp.domain.usecase

import nl.wesselbarten.museumapp.data.ResultWrapper
import nl.wesselbarten.museumapp.domain.model.ExtendedArtObject
import nl.wesselbarten.museumapp.domain.repository.ArtObjectRepository
import javax.inject.Inject

class GetArtObjectByObjectNumberUseCase @Inject constructor(
    private val artObjectRepository: ArtObjectRepository
) {

    suspend fun execute(objectNumber: String): ResultWrapper<ExtendedArtObject> {
        return artObjectRepository.findByObjectNumber(objectNumber)
    }
}