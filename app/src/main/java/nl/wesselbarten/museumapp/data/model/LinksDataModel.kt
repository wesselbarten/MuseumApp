package nl.wesselbarten.museumapp.data.model

import com.google.gson.annotations.SerializedName
import nl.wesselbarten.museumapp.domain.model.Links

data class LinksDataModel(
    @SerializedName("self")
    val self: String?,
    @SerializedName("web")
    val web: String?
) {

    fun toDomainModel(): Links {
        return Links(self, web)
    }
}