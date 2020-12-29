package nl.wesselbarten.museumapp.domain.model

data class ArtObject(
    val objectNumber: String,
    val id: String,
    val links: Links,
    val title: String,
    val hasImage: Boolean,
    val principalOrFirstMaker: String?,
    val longTitle: String,
    val showImage: Boolean,
    val permitDownload: Boolean,
    val webImage: WebImage?,
    val headerImage: HeaderImage?,
    val productionPlaces: List<String>,
)