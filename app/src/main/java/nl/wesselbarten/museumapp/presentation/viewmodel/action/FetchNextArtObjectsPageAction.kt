package nl.wesselbarten.museumapp.presentation.viewmodel.action

sealed class FetchNextArtObjectsPageAction {

    object Loading : FetchNextArtObjectsPageAction()

    object Finished : FetchNextArtObjectsPageAction()

    data class Error(val errorMessage: String) : FetchNextArtObjectsPageAction()
}