package nl.wesselbarten.museumapp.presentation.viewmodel.state

import nl.wesselbarten.museumapp.domain.model.ArtObject

sealed class GetArtObjectsState {

    object Loading : GetArtObjectsState()

    data class Success(val items: List<ArtObject>) : GetArtObjectsState()

    data class Error(val errorMessage: String): GetArtObjectsState()
}