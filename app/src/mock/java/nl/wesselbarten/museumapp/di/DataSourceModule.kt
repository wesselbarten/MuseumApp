package nl.wesselbarten.museumapp.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import nl.wesselbarten.museumapp.data.source.ArtObjectsDataSource
import nl.wesselbarten.museumapp.data.source.FakeArtObjectsDataSource
import nl.wesselbarten.museumapp.util.coroutines.DispatcherProvider
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object DataSourceModule {

    @Provides
    @Singleton
    fun provideArtObjectsDataSource(): ArtObjectsDataSource {
        return FakeArtObjectsDataSource()
    }
}