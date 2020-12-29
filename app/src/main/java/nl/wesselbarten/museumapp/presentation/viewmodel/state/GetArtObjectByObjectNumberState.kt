package nl.wesselbarten.museumapp.presentation.viewmodel.state

import nl.wesselbarten.museumapp.domain.model.ExtendedArtObject

sealed class GetArtObjectByObjectNumberState {

    object Loading : GetArtObjectByObjectNumberState()

    data class Finished(val artObject: ExtendedArtObject) : GetArtObjectByObjectNumberState()

    data class Error(val errorMessage: String) : GetArtObjectByObjectNumberState()
}