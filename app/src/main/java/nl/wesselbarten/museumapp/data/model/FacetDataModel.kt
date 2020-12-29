package nl.wesselbarten.museumapp.data.model

import com.google.gson.annotations.SerializedName

data class FacetDataModel(
    @SerializedName("facets")
    val facets: List<FacetXDataModel>,
    @SerializedName("name")
    val name: String,
    @SerializedName("otherTerms")
    val otherTerms: Int,
    @SerializedName("prettyName")
    val prettyName: Int
)