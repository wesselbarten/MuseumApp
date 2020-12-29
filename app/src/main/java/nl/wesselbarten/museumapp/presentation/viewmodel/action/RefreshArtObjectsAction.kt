package nl.wesselbarten.museumapp.presentation.viewmodel.action

sealed class RefreshArtObjectsAction {

    object Finished : RefreshArtObjectsAction()

    data class Error(val errorMessage: String) : RefreshArtObjectsAction()
}