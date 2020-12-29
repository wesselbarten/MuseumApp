package nl.wesselbarten.museumapp.data.model

import com.google.gson.annotations.SerializedName
import nl.wesselbarten.museumapp.domain.model.WebImage

data class WebImageDataModel(
    @SerializedName("guid")
    val guid: String,
    @SerializedName("width")
    val width: Int,
    @SerializedName("height")
    val height: Int,
    @SerializedName("offsetPercentageX")
    val offsetPercentageX: Int,
    @SerializedName("offsetPercentageY")
    val offsetPercentageY: Int,
    @SerializedName("url")
    val url: String
) {

    fun toDomainModel(): WebImage {
        return WebImage(
            guid = guid,
            width = width,
            height = height,
            offsetPercentageX = offsetPercentageX,
            offsetPercentageY = offsetPercentageY,
            url = url
        )
    }
}