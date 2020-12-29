package nl.wesselbarten.museumapp.data.network

import nl.wesselbarten.museumapp.data.network.response.GetAllArtObjectsResponse
import nl.wesselbarten.museumapp.data.network.response.GetArtObjectByObjectNumberResponse
import nl.wesselbarten.museumapp.data.repository.ART_OBJECTS_PAGE_SIZE
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ArtCollectionApiService {

    @GET("collection/")
    suspend fun findAll(
        @Query("p") page: Int,
        @Query("ps") resultsPerPage: Int = ART_OBJECTS_PAGE_SIZE,
        @Query("s") sorting: String = "relevance"
    ): GetAllArtObjectsResponse

    @GET("collection/{objectNumber}")
    suspend fun findByObjectNumber(@Path("objectNumber") objectNumber: String): GetArtObjectByObjectNumberResponse
}