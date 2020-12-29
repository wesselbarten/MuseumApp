package nl.wesselbarten.museumapp.data.model

import com.google.gson.annotations.SerializedName

data class FacetXDataModel(
    @SerializedName("key")
    val key: String,
    @SerializedName("value")
    val value: Int
)