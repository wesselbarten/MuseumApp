package nl.wesselbarten.museumapp.presentation.artcollection

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.appmattus.kotlinfixture.kotlinFixture
import com.nhaarman.mockitokotlin2.*
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runBlockingTest
import nl.wesselbarten.museumapp.util.MainCoroutineScopeRule
import nl.wesselbarten.museumapp.data.ResultWrapper
import nl.wesselbarten.museumapp.domain.model.ArtObject
import nl.wesselbarten.museumapp.domain.usecase.FetchNextArtObjectsPageUseCase
import nl.wesselbarten.museumapp.domain.usecase.GetArtObjectByObjectNumberUseCase
import nl.wesselbarten.museumapp.domain.usecase.GetArtObjectsUseCase
import nl.wesselbarten.museumapp.domain.usecase.RefreshArtObjectsUseCase
import nl.wesselbarten.museumapp.presentation.viewmodel.state.GetArtObjectsState
import nl.wesselbarten.museumapp.presentation.viewmodel.action.RefreshArtObjectsAction
import nl.wesselbarten.museumapp.presentation.error.ErrorHandler
import nl.wesselbarten.museumapp.presentation.viewmodel.ArtCollectionViewModel
import nl.wesselbarten.museumapp.util.event.Event
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.ArgumentCaptor
import org.mockito.Captor
import org.mockito.Mock
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
class ArtCollectionViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineScope = MainCoroutineScopeRule()

    @Mock
    private lateinit var errorHandler: ErrorHandler

    @Mock
    private lateinit var fetchNextArtObjectsPageUseCase: FetchNextArtObjectsPageUseCase

    @Mock
    private lateinit var getArtObjectByObjectNumberUseCase: GetArtObjectByObjectNumberUseCase

    @Mock
    private lateinit var getArtObjectsUseCase: GetArtObjectsUseCase

    @Mock
    private lateinit var refreshArtObjectsUseCase: RefreshArtObjectsUseCase

    @Captor
    private lateinit var getArtObjectsCaptor: ArgumentCaptor<GetArtObjectsState>

    @Captor
    private lateinit var refreshArtObjectsCaptor: ArgumentCaptor<Event<RefreshArtObjectsAction>>

    private lateinit var viewModel: ArtCollectionViewModel

    private val fixture = kotlinFixture()

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        viewModel = ArtCollectionViewModel(
            errorHandler,
            fetchNextArtObjectsPageUseCase,
            getArtObjectByObjectNumberUseCase,
            getArtObjectsUseCase,
            refreshArtObjectsUseCase
        )
    }

    @Test
    fun `test get art objects shows items`() = coroutineScope.runBlockingTest {
        val artObjects: List<ArtObject> = listOf(fixture())

        val resultSuccessFlow = flowOf(ResultWrapper.Success(artObjects))

        whenever(getArtObjectsUseCase.execute())
            .thenReturn(resultSuccessFlow)

        viewModel.getArtObjects()

        val mockObserver = mock<Observer<GetArtObjectsState>>()
        viewModel.getArtObjectsState.observeForever(mockObserver)

        verify(mockObserver).onChanged(getArtObjectsCaptor.capture())
        verify(getArtObjectsUseCase, times(1)).execute()
        verify(errorHandler, times(0)).handleError(any(), any())

        assertTrue(getArtObjectsCaptor.value is GetArtObjectsState.Success)
        assertEquals(
            artObjects.size,
            (getArtObjectsCaptor.value as GetArtObjectsState.Success).items.size
        )

        viewModel.getArtObjectsState.removeObserver(mockObserver)
    }

    @Test
    fun `test get art objects returns error`() = coroutineScope.runBlockingTest {
        val resultErrorFlow = flowOf(ResultWrapper.Error(Exception()))

        whenever(getArtObjectsUseCase.execute())
            .thenReturn(resultErrorFlow)

        whenever(errorHandler.handleError(any(), any()))
            .thenReturn("error")

        viewModel.getArtObjects()

        val mockObserver = mock<Observer<GetArtObjectsState>>()
        viewModel.getArtObjectsState.observeForever(mockObserver)

        verify(mockObserver).onChanged(getArtObjectsCaptor.capture())
        verify(getArtObjectsUseCase, times(1)).execute()
        verify(errorHandler, times(1)).handleError(any(), any())

        assertTrue(getArtObjectsCaptor.value is GetArtObjectsState.Error)

        viewModel.getArtObjectsState.removeObserver(mockObserver)
    }

    @Test
    fun `test refresh art objects is successful`() = coroutineScope.runBlockingTest {
        whenever(refreshArtObjectsUseCase.execute())
            .thenReturn(ResultWrapper.Success(Unit))

        viewModel.refreshArtObjects()

        val mockObserver = mock<Observer<Event<RefreshArtObjectsAction>>>()
        viewModel.refreshArtObjectsAction.observeForever(mockObserver)

        verify(mockObserver).onChanged(refreshArtObjectsCaptor.capture())
        verify(refreshArtObjectsUseCase, times(1)).execute()
        verify(errorHandler, times(0)).handleError(any(), any())

        assertTrue(refreshArtObjectsCaptor.value.peekContent() is RefreshArtObjectsAction.Finished)

        viewModel.refreshArtObjectsAction.removeObserver(mockObserver)
    }

    @Test
    fun `test refresh art objects returns error`() = coroutineScope.runBlockingTest {
        whenever(refreshArtObjectsUseCase.execute())
            .thenReturn(ResultWrapper.Error(Exception()))

        whenever(errorHandler.handleError(any(), any()))
            .thenReturn("error")

        viewModel.refreshArtObjects()

        val mockObserver = mock<Observer<Event<RefreshArtObjectsAction>>>()
        viewModel.refreshArtObjectsAction.observeForever(mockObserver)

        verify(mockObserver).onChanged(refreshArtObjectsCaptor.capture())
        verify(refreshArtObjectsUseCase, times(1)).execute()
        verify(errorHandler, times(1)).handleError(any(), any())

        assertTrue(refreshArtObjectsCaptor.value.peekContent() is RefreshArtObjectsAction.Error)

        viewModel.refreshArtObjectsAction.removeObserver(mockObserver)
    }
}