package nl.wesselbarten.museumapp.data.model

import com.google.gson.annotations.SerializedName

data class CountFacetsDataModel(
    @SerializedName("hasimage")
    val hasImage: Int,
    @SerializedName("ondisplay")
    val onDisplay: Int
)