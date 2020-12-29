package nl.wesselbarten.museumapp.data.model

import com.google.gson.annotations.SerializedName
import nl.wesselbarten.museumapp.domain.model.HeaderImage

data class HeaderImageDataModel(
    @SerializedName("guid")
    val guid: String?,
    @SerializedName("width")
    val width: Int,
    @SerializedName("height")
    val height: Int,
    @SerializedName("offsetPercentageX")
    val offsetPercentageX: Int,
    @SerializedName("offsetPercentageY")
    val offsetPercentageY: Int,
    @SerializedName("url")
    val url: String?
) {

    fun toDomainModel(): HeaderImage {
        return HeaderImage(
            guid = guid,
            width = width,
            height = height,
            offsetPercentageX = offsetPercentageX,
            offsetPercentageY = offsetPercentageY,
            url = url
        )
    }
}