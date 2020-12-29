package nl.wesselbarten.museumapp.di

import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import nl.wesselbarten.museumapp.BuildConfig
import nl.wesselbarten.museumapp.data.network.ArtCollectionApiService
import nl.wesselbarten.museumapp.data.network.RestClient
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideRestClient(gson: Gson): RestClient {
        return RestClient(
            BuildConfig.ART_COLLECTION_API_URL,
            BuildConfig.ART_COLLECTION_API_KEY,
            BuildConfig.REQUEST_LOGGING_ENABLED,
            gson
        )
    }

    @Provides
    @Singleton
    fun provideArtCollectionApiService(restClient: RestClient): ArtCollectionApiService {
        return restClient.getArtCollectionApiService()
    }
}