package nl.wesselbarten.museumapp.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import nl.wesselbarten.museumapp.domain.repository.ArtObjectRepository
import nl.wesselbarten.museumapp.domain.usecase.FetchNextArtObjectsPageUseCase
import nl.wesselbarten.museumapp.domain.usecase.GetArtObjectByObjectNumberUseCase
import nl.wesselbarten.museumapp.domain.usecase.GetArtObjectsUseCase
import nl.wesselbarten.museumapp.domain.usecase.RefreshArtObjectsUseCase

@Module
@InstallIn(ApplicationComponent::class)
object UseCaseModule {

    @Provides
    fun provideFetchNextArtObjectsPageUseCase(artObjectRepository: ArtObjectRepository): FetchNextArtObjectsPageUseCase {
        return FetchNextArtObjectsPageUseCase(artObjectRepository)
    }

    @Provides
    fun provideGetArtObjectByObjectNumberUseCase(artObjectRepository: ArtObjectRepository): GetArtObjectByObjectNumberUseCase {
        return GetArtObjectByObjectNumberUseCase(artObjectRepository)
    }

    @Provides
    fun provideGetArtObjectsUseCase(artObjectRepository: ArtObjectRepository): GetArtObjectsUseCase {
        return GetArtObjectsUseCase(artObjectRepository)
    }

    @Provides
    fun provideRefreshArtObjectsUseCase(artObjectRepository: ArtObjectRepository): RefreshArtObjectsUseCase {
        return RefreshArtObjectsUseCase(artObjectRepository)
    }
}