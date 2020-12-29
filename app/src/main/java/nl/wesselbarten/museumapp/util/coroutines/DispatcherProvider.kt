package nl.wesselbarten.museumapp.util.coroutines

import kotlinx.coroutines.CoroutineDispatcher

interface DispatcherProvider {

    fun default(): CoroutineDispatcher

    fun io(): CoroutineDispatcher

    fun ui(): CoroutineDispatcher
}