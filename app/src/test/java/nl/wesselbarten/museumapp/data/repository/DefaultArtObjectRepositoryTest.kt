package nl.wesselbarten.museumapp.data.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.appmattus.kotlinfixture.kotlinFixture
import com.nhaarman.mockitokotlin2.*
import dev.olog.flow.test.observer.test
import junit.framework.Assert.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import nl.wesselbarten.museumapp.data.ResultWrapper
import nl.wesselbarten.museumapp.data.source.ArtObjectsDataSource
import nl.wesselbarten.museumapp.domain.model.ArtObject
import nl.wesselbarten.museumapp.util.MainCoroutineScopeRule
import nl.wesselbarten.museumapp.util.TestDispatcherProvider
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.time.ExperimentalTime

@ExperimentalTime
@ExperimentalCoroutinesApi
class DefaultArtObjectRepositoryTest {

    private val testDispatcher = TestCoroutineDispatcher()

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineScope = MainCoroutineScopeRule(testDispatcher)

    private lateinit var repository: DefaultArtObjectRepository

    private lateinit var dispatcherProvider: TestDispatcherProvider


    private val fixture = kotlinFixture()

    @Before
    fun setup() {
        dispatcherProvider = TestDispatcherProvider(testDispatcher)
    }

    @Test
    fun `test get art objects is successful`() = testDispatcher.runBlockingTest {
        val artObjects: List<ArtObject> = listOf(fixture())

        val artObjectsDataSource = mock<ArtObjectsDataSource> {
            whenever(mock.getAll(any()))
                .thenReturn(ResultWrapper.Success(artObjects))
        }

        repository = DefaultArtObjectRepository(dispatcherProvider, artObjectsDataSource)

        repository.getAll().test(this) {
            assertValueCount(1)
            assertValue { it is ResultWrapper.Success }
        }

        verify(artObjectsDataSource, times(1)).getAll(any())
    }

    @Test
    fun `test get art objects returns error`() = testDispatcher.runBlockingTest {
        val artObjectsDataSource = mock<ArtObjectsDataSource> {
            whenever(mock.getAll(any()))
                .thenReturn(ResultWrapper.Error(Exception()))
        }

        repository = DefaultArtObjectRepository(dispatcherProvider, artObjectsDataSource)

        repository.getAll().test(this) {
            assertValueCount(1)
            assertValue { it is ResultWrapper.Error }
        }

        verify(artObjectsDataSource, times(1)).getAll(any())
    }

    @Test
    fun `test refresh art objects is successful`() = testDispatcher.runBlockingTest {
        val artObjects: List<ArtObject> = listOf(fixture())

        val artObjectsDataSource = mock<ArtObjectsDataSource> {
            whenever(mock.getAll(any()))
                .thenReturn(ResultWrapper.Success(artObjects))
        }

        repository = DefaultArtObjectRepository(dispatcherProvider, artObjectsDataSource)

        repository.getAll().test(this) {
            assertValueCount(2)
            assertValueAt(0) { it is ResultWrapper.Success }
            assertValueAt(1) { it is ResultWrapper.Success }
        }

        whenever(artObjectsDataSource.getAll(any()))
            .thenReturn(ResultWrapper.Success(artObjects))

        val result = repository.refreshAll()
        assertTrue(result is ResultWrapper.Success)

        verify(artObjectsDataSource, times(2)).getAll(any())
    }

    @Test
    fun `test refresh art objects returns error`() = testDispatcher.runBlockingTest {
        val artObjects: List<ArtObject> = listOf(fixture())

        val artObjectsDataSource = mock<ArtObjectsDataSource> {
            whenever(mock.getAll(any()))
                .thenReturn(ResultWrapper.Success(artObjects))
        }

        repository = DefaultArtObjectRepository(dispatcherProvider, artObjectsDataSource)

        repository.getAll().test(this) {
            assertValueCount(1)
            assertValue { it is ResultWrapper.Success }
        }

        whenever(artObjectsDataSource.getAll(any()))
            .thenReturn(ResultWrapper.Error(Exception()))

        val result = repository.refreshAll()
        assertTrue(result is ResultWrapper.Error)

        verify(artObjectsDataSource, times(2)).getAll(any())
    }
}