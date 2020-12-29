package nl.wesselbarten.museumapp.util

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import nl.wesselbarten.museumapp.util.coroutines.DispatcherProvider

@ExperimentalCoroutinesApi
class TestDispatcherProvider(
    private val testDispatcher: TestCoroutineDispatcher
) : DispatcherProvider {

    override fun default(): CoroutineDispatcher = testDispatcher

    override fun io(): CoroutineDispatcher = testDispatcher

    override fun ui(): CoroutineDispatcher = testDispatcher

}