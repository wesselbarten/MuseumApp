package nl.wesselbarten.museumapp.presentation.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import nl.wesselbarten.museumapp.data.ResultWrapper
import nl.wesselbarten.museumapp.domain.usecase.FetchNextArtObjectsPageUseCase
import nl.wesselbarten.museumapp.domain.usecase.GetArtObjectByObjectNumberUseCase
import nl.wesselbarten.museumapp.domain.usecase.GetArtObjectsUseCase
import nl.wesselbarten.museumapp.domain.usecase.RefreshArtObjectsUseCase
import nl.wesselbarten.museumapp.presentation.error.ErrorCodes
import nl.wesselbarten.museumapp.presentation.error.ErrorHandler
import nl.wesselbarten.museumapp.presentation.viewmodel.action.FetchNextArtObjectsPageAction
import nl.wesselbarten.museumapp.presentation.viewmodel.action.RefreshArtObjectsAction
import nl.wesselbarten.museumapp.presentation.viewmodel.state.GetArtObjectByObjectNumberState
import nl.wesselbarten.museumapp.presentation.viewmodel.state.GetArtObjectsState
import nl.wesselbarten.museumapp.util.event.Event
import nl.wesselbarten.museumapp.util.event.toEvent

class ArtCollectionViewModel @ViewModelInject constructor(
    private val errorHandler: ErrorHandler,
    private val fetchNextArtObjectsPageUseCase: FetchNextArtObjectsPageUseCase,
    private val getArtObjectByObjectNumberUseCase: GetArtObjectByObjectNumberUseCase,
    private val getArtObjectsUseCase: GetArtObjectsUseCase,
    private val refreshArtObjectsUseCase: RefreshArtObjectsUseCase
) : ViewModel() {

    private val _getArtObjectsState = MutableLiveData<GetArtObjectsState>()
    val getArtObjectsState: LiveData<GetArtObjectsState> get() = _getArtObjectsState

    private val _getArtObjectByObjectNumberState =
        MutableLiveData<GetArtObjectByObjectNumberState>()
    val getArtObjectByObjectNumberState: LiveData<GetArtObjectByObjectNumberState>
        get() = _getArtObjectByObjectNumberState

    private val _refreshArtObjectsAction = MutableLiveData<Event<RefreshArtObjectsAction>>()
    val refreshArtObjectsAction: LiveData<Event<RefreshArtObjectsAction>>
        get() = _refreshArtObjectsAction

    private val _fetchNextArtObjectsPageAction =
        MutableLiveData<Event<FetchNextArtObjectsPageAction>>()
    val fetchNextArtObjectsPageAction: LiveData<Event<FetchNextArtObjectsPageAction>>
        get() = _fetchNextArtObjectsPageAction

    private var canFetchNextPage: Boolean = false
    private var selectedArtObjectNumber: String = ""

    fun getArtObjects() {
        canFetchNextPage = false
        _getArtObjectsState.value = GetArtObjectsState.Loading

        viewModelScope.launch {
            getArtObjectsUseCase.execute().collectLatest { result ->
                canFetchNextPage = true
                _getArtObjectsState.value = when (result) {
                    is ResultWrapper.Success -> {
                        GetArtObjectsState.Success(result.data)
                    }
                    is ResultWrapper.Error -> GetArtObjectsState.Error(
                        errorHandler.handleError(
                            result.exception,
                            ErrorCodes.UNABLE_TO_GET_ART_OBJECTS
                        )
                    )
                }
            }
        }
    }

    fun refreshArtObjects() {
        viewModelScope.launch {
            when (val result = refreshArtObjectsUseCase.execute()) {
                is ResultWrapper.Success -> {
                    _refreshArtObjectsAction.value = RefreshArtObjectsAction.Finished.toEvent()
                }
                is ResultWrapper.Error -> {
                    _refreshArtObjectsAction.value = RefreshArtObjectsAction.Error(
                        errorHandler.handleError(
                            result.exception,
                            ErrorCodes.UNABLE_TO_REFRESH_ART_OBJECTS
                        )
                    ).toEvent()
                }
            }
        }
    }

    fun showArtObjectDetails(objectNumber: String) {
        selectedArtObjectNumber = objectNumber
        _getArtObjectByObjectNumberState.value = GetArtObjectByObjectNumberState.Loading
        viewModelScope.launch {
            when (val result = getArtObjectByObjectNumberUseCase.execute(objectNumber)) {
                is ResultWrapper.Success -> {
                    _getArtObjectByObjectNumberState.value = GetArtObjectByObjectNumberState.Finished(result.data)
                }
                is ResultWrapper.Error -> {
                    _getArtObjectByObjectNumberState.value = GetArtObjectByObjectNumberState.Error(
                        errorHandler.handleError(
                            result.exception,
                            ErrorCodes.UNABLE_TO_FIND_ART_OBJECT
                        )
                    )
                }
            }
        }
    }

    fun getNextPage() {
        if (canFetchNextPage) {
            canFetchNextPage = false

            _fetchNextArtObjectsPageAction.value = FetchNextArtObjectsPageAction.Loading.toEvent()

            viewModelScope.launch {
                val result = fetchNextArtObjectsPageUseCase.execute()

                _fetchNextArtObjectsPageAction.value = when (result) {
                    is ResultWrapper.Success -> {
                        canFetchNextPage = true
                        FetchNextArtObjectsPageAction.Finished
                    }
                    is ResultWrapper.Error -> {
                        canFetchNextPage = true
                        FetchNextArtObjectsPageAction.Error(
                            errorHandler.handleError(
                                result.exception,
                                ErrorCodes.UNABLE_TO_GET_NEXT_ART_OBJECTS_PAGE
                            )
                        )
                    }
                }.toEvent()
            }
        }
    }

    fun retryGetArtObjectByObjectNumber() {
        showArtObjectDetails(selectedArtObjectNumber)
    }
}