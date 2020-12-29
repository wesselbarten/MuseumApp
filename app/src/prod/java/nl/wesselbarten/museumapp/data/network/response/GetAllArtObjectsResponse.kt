package nl.wesselbarten.museumapp.data.network.response

import com.google.gson.annotations.SerializedName
import nl.wesselbarten.museumapp.data.model.ArtObjectDataModel
import nl.wesselbarten.museumapp.data.model.CountFacetsDataModel
import nl.wesselbarten.museumapp.data.model.FacetDataModel

data class GetAllArtObjectsResponse(
    @SerializedName("artObjects")
    val artObjects: List<ArtObjectDataModel>,
    @SerializedName("count")
    val count: Int,
    @SerializedName("countFacets")
    val countFacets: CountFacetsDataModel,
    @SerializedName("elapsedMilliseconds")
    val elapsedMilliseconds: Int,
    @SerializedName("facets")
    val facets: List<FacetDataModel>
)













