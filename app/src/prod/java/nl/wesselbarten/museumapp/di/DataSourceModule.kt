package nl.wesselbarten.museumapp.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import nl.wesselbarten.museumapp.data.network.ArtCollectionApiService
import nl.wesselbarten.museumapp.data.source.ArtObjectsDataSource
import nl.wesselbarten.museumapp.data.source.RemoteArtObjectsDataSource
import nl.wesselbarten.museumapp.util.coroutines.DispatcherProvider
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object DataSourceModule {

    @Provides
    @Singleton
    fun provideArtObjectsDataSource(artCollectionApiService: ArtCollectionApiService, dispatcherProvider: DispatcherProvider): ArtObjectsDataSource {
        return RemoteArtObjectsDataSource(artCollectionApiService, dispatcherProvider)
    }
}