package nl.wesselbarten.museumapp.data.model

import com.google.gson.annotations.SerializedName
import nl.wesselbarten.museumapp.domain.model.ArtObjectPage

data class ArtObjectPageDataModel(
    @SerializedName("objectNumber")
    val objectNumber: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("createdOn")
    val createdOn: String,
    @SerializedName("updatedOn")
    val updatedOn: String,
    @SerializedName("lang")
    val lang: String,
    @SerializedName("audioFile1")
    val audioFile1: Any,
    @SerializedName("audioFileLabel1")
    val audioFileLabel1: Any,
    @SerializedName("audioFileLabel2")
    val audioFileLabel2: Any,
    @SerializedName("plaqueDescription")
    val plaqueDescription: String,
    @SerializedName("similarPages")
    val similarPages: List<Any>,
    @SerializedName("tags")
    val tags: List<Any>,
) {

    fun toDomainModel(): ArtObjectPage {
        return ArtObjectPage(
            id = id,
            objectNumber = objectNumber,
            createdOn = createdOn,
            updatedOn = updatedOn,
            lang = lang,
            audioFile1 = audioFile1,
            audioFileLabel1 = audioFileLabel1,
            audioFileLabel2 = audioFileLabel2,
            plaqueDescription = plaqueDescription,
            similarPages = similarPages,
            tags = tags
        )
    }
}