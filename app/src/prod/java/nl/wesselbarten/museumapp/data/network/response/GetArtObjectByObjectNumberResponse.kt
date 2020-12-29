package nl.wesselbarten.museumapp.data.network.response

import com.google.gson.annotations.SerializedName
import nl.wesselbarten.museumapp.data.model.ArtObjectPageDataModel
import nl.wesselbarten.museumapp.data.model.ExtendedArtObjectDataModel

data class GetArtObjectByObjectNumberResponse(
    @SerializedName("artObject")
    val artObject: ExtendedArtObjectDataModel,
    @SerializedName("artObjectPage")
    val artObjectPage: ArtObjectPageDataModel,
    @SerializedName("elapsedMilliseconds")
    val elapsedMilliseconds: Int
)