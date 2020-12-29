package nl.wesselbarten.museumapp.domain.model

data class ArtObjectPage(
    val objectNumber: String,
    val id: String,
    val createdOn: String,
    val updatedOn: String,
    val lang: String,
    val audioFile1: Any,
    val audioFileLabel1: Any,
    val audioFileLabel2: Any,
    val plaqueDescription: String,
    val similarPages: List<Any>,
    val tags: List<Any>,
)