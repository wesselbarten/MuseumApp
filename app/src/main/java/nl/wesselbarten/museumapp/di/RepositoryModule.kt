package nl.wesselbarten.museumapp.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import nl.wesselbarten.museumapp.data.repository.DefaultArtObjectRepository
import nl.wesselbarten.museumapp.data.source.ArtObjectsDataSource
import nl.wesselbarten.museumapp.domain.repository.ArtObjectRepository
import nl.wesselbarten.museumapp.util.coroutines.DispatcherProvider
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideArtObjectRepository(dispatcherProvider: DispatcherProvider, artObjectsDataSource: ArtObjectsDataSource): ArtObjectRepository {
        return DefaultArtObjectRepository(dispatcherProvider, artObjectsDataSource)
    }
}