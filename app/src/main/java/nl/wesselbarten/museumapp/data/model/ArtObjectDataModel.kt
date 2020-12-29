package nl.wesselbarten.museumapp.data.model

import com.google.gson.annotations.SerializedName
import nl.wesselbarten.museumapp.domain.model.ArtObject

data class ArtObjectDataModel(
    @SerializedName("objectNumber")
    val objectNumber: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("links")
    val links: LinksDataModel,
    @SerializedName("title")
    val title: String,
    @SerializedName("hasImage")
    val hasImage: Boolean,
    @SerializedName("principalOfFirstMaker")
    val principalOrFirstMaker: String?,
    @SerializedName("longTitle")
    val longTitle: String,
    @SerializedName("showImage")
    val showImage: Boolean,
    @SerializedName("permitDownload")
    val permitDownload: Boolean,
    @SerializedName("webImage")
    val webImage: WebImageDataModel?,
    @SerializedName("headerImage")
    val headerImage: HeaderImageDataModel?,
    @SerializedName("productionPlaces")
    val productionPlaces: List<String>
) {

    fun toDomainModel(): ArtObject {
        return ArtObject(
            objectNumber = objectNumber,
            id = id,
            links = links.toDomainModel(),
            title = title,
            hasImage = hasImage,
            principalOrFirstMaker = principalOrFirstMaker,
            longTitle = longTitle,
            showImage = showImage,
            permitDownload = permitDownload,
            webImage = webImage?.toDomainModel(),
            headerImage = headerImage?.toDomainModel(),
            productionPlaces = productionPlaces
        )
    }
}