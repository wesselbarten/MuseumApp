package nl.wesselbarten.museumapp.data.model

import com.google.gson.annotations.SerializedName
import nl.wesselbarten.museumapp.domain.model.ExtendedArtObject

class ExtendedArtObjectDataModel(
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
    val productionPlaces: List<String>,
    @SerializedName("description")
    val description: String?,
    @SerializedName("documentation")
    val documentation: List<String>,
    @SerializedName("exhibitions")
    val exhibitions: List<Any>,
    @SerializedName("labelText")
    val labelText: Any?,
    @SerializedName("language")
    val language: String,
    @SerializedName("location")
    val location: String?,
    @SerializedName("makers")
    val makers: List<Any>,
    @SerializedName("materials")
    val materials: List<String>,
    @SerializedName("objectsCollections")
    val objectCollection: List<String>?,
    @SerializedName("objectTypes")
    val objectTypes: List<String>,
    @SerializedName("physicalMedium")
    val physicalMedium: String,
    @SerializedName("physicalProperties")
    val physicalProperties: List<Any>,
    @SerializedName("plaqueDescriptionDutch")
    val plaqueDescriptionDutch: String?,
    @SerializedName("plaqueDescriptionEnglish")
    val plaqueDescriptionEnglish: String?,
    @SerializedName("principalMaker")
    val principalMaker: String,
    @SerializedName("principalMakers")
    val principalMakers: List<PrincipalMakerDataModel>,
    @SerializedName("subTitle")
    val subTitle: String,
    @SerializedName("techniques")
    val techniques: List<Any>,
    @SerializedName("titles")
    val titles: List<String>
) {

    fun toDomainModel(): ExtendedArtObject {
        return ExtendedArtObject(
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
            productionPlaces = productionPlaces,
            description = description,
            documentation = documentation,
            exhibitions = exhibitions,
            labelText = labelText,
            language = language,
            location = location,
            makers = makers,
            materials = materials,
            objectCollection = objectCollection,
            objectTypes = objectTypes,
            physicalMedium = physicalMedium,
            physicalProperties = physicalProperties,
            plaqueDescriptionDutch = plaqueDescriptionDutch,
            plaqueDescriptionEnglish = plaqueDescriptionEnglish,
            principalMaker = principalMaker,
            principalMakers = principalMakers.map { it.toDomainModel() },
            subTitle = subTitle,
            techniques = techniques,
            titles = titles
        )
    }
}